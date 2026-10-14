package com.alpesh.creditscope.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final JdbcTemplate jdbc;

    public ReportController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/risk-distribution")
    public List<Map<String, Object>> riskDistribution() {
        return jdbc.queryForList("""
            SELECT risk_band, COUNT(*) AS applications,
                   ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) AS pct
            FROM v_latest_score
            GROUP BY risk_band
            """);
    }

    @GetMapping("/top-risk-by-employment-type")
    public List<Map<String, Object>> topRisk() {
        return jdbc.queryForList("""
            SELECT * FROM (
              SELECT employment_type, name, total_score, risk_band,
                     RANK() OVER (PARTITION BY employment_type
                                  ORDER BY total_score ASC) AS risk_rank
              FROM v_applicant_risk_summary
            ) r
            WHERE risk_rank <= 3
            ORDER BY employment_type, risk_rank
            """);
    }

    @GetMapping("/default-trend")
    public List<Map<String, Object>> defaultTrend() {
        return jdbc.queryForList("""
            SELECT month, missed_pct,
                   ROUND(AVG(missed_pct) OVER (ORDER BY month
                         ROWS BETWEEN 2 PRECEDING AND CURRENT ROW), 2) AS moving_avg_3m
            FROM (
              SELECT DATE_FORMAT(due_date, '%Y-%m-01') AS month,
                     ROUND(SUM(CASE WHEN status = 'MISSED' THEN 1 ELSE 0 END) * 100.0
                           / COUNT(*), 2) AS missed_pct
              FROM repayment_history
              GROUP BY DATE_FORMAT(due_date, '%Y-%m-01')) m
            ORDER BY month
            """);
    }
}