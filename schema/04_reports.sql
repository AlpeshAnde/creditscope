-- CreditScope: analytics queries (MySQL 8.0). These back the /api/reports endpoints.
USE creditscope;

-- 1) Risk distribution: count and % per band (window function over aggregate)
SELECT risk_band, COUNT(*) AS applications,
       ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) AS pct
FROM v_latest_score
GROUP BY risk_band;

-- 2) Three riskiest applicants within each employment type (RANK per partition)
SELECT * FROM (
                  SELECT employment_type, name, total_score, risk_band,
                         RANK() OVER (PARTITION BY employment_type ORDER BY total_score ASC) AS risk_rank
                  FROM v_applicant_risk_summary
              ) r
WHERE risk_rank <= 3
ORDER BY employment_type, risk_rank;

-- 3) Missed-payment trend with a 3-month moving average (CTE + window frame)
WITH monthly AS (
    SELECT DATE_FORMAT(due_date, '%Y-%m-01') AS month,
    SUM(CASE WHEN status = 'MISSED' THEN 1 ELSE 0 END) AS missed_count,
    COUNT(*) AS total_count
FROM repayment_history
GROUP BY DATE_FORMAT(due_date, '%Y-%m-01')
    )
SELECT month, missed_count, total_count,
    ROUND(missed_count * 100.0 / total_count, 2) AS missed_pct,
    ROUND(AVG(missed_count * 100.0 / total_count)
    OVER (ORDER BY month ROWS BETWEEN 2 PRECEDING AND CURRENT ROW), 2) AS moving_avg_3mo
FROM monthly
ORDER BY month;

-- 4) Applicants scoring below the average of their employment type (CTE)
WITH type_avg AS (
    SELECT employment_type, AVG(total_score) AS avg_score
    FROM v_applicant_risk_summary
    GROUP BY employment_type
)
SELECT s.name, s.employment_type, s.total_score, ROUND(t.avg_score, 1) AS type_avg
FROM v_applicant_risk_summary s
         JOIN type_avg t ON t.employment_type = s.employment_type
WHERE s.total_score < t.avg_score
ORDER BY s.total_score;