-- CreditScope: reusable views (MySQL 8.0). Run after the app has created its tables.
USE creditscope;

-- Latest score per loan application (an application can be scored more than once)
CREATE OR REPLACE VIEW v_latest_score AS
SELECT id, application_id, total_score, risk_band, computed_at
FROM (
         SELECT csr.*,
                ROW_NUMBER() OVER (PARTITION BY application_id
                            ORDER BY computed_at DESC, id DESC) AS rn
         FROM credit_score_result csr
     ) t
WHERE rn = 1;

-- One row per application: applicant, employment, loan amount, latest score and band
CREATE OR REPLACE VIEW v_applicant_risk_summary AS
SELECT a.id AS applicant_id, u.name, a.employment_type, a.monthly_income,
       la.id AS application_id, la.loan_amount,
       ls.total_score, ls.risk_band, ls.computed_at
FROM v_latest_score ls
         JOIN loan_application la ON la.id = ls.application_id
         JOIN applicant a ON a.id = la.applicant_id
         JOIN `user` u ON u.id = a.user_id;