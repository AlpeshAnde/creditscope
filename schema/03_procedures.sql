-- CreditScope: stored procedures
USE creditscope;

DROP PROCEDURE IF EXISTS sp_applicants_by_risk;
DELIMITER //
CREATE PROCEDURE sp_applicants_by_risk(IN p_band VARCHAR(10), IN p_limit INT)
BEGIN
SELECT applicant_id, name, employment_type, loan_amount, total_score, risk_band
FROM v_applicant_risk_summary
WHERE risk_band = p_band
ORDER BY total_score ASC
    LIMIT p_limit;
END//
DELIMITER ;

-- Example: CALL sp_applicants_by_risk('HIGH', 5);