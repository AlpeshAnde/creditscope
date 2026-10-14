-- CreditScope: audit trail for loan application status changes
USE creditscope;

CREATE TABLE IF NOT EXISTS loan_application_audit (
                                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                      application_id BIGINT NOT NULL,
                                                      old_status VARCHAR(30),
    new_status VARCHAR(30),
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

DROP TRIGGER IF EXISTS trg_loan_application_status_audit;

DELIMITER //
CREATE TRIGGER trg_loan_application_status_audit
    AFTER UPDATE ON loan_application
    FOR EACH ROW
BEGIN
    IF NOT (OLD.status <=> NEW.status) THEN
    INSERT INTO loan_application_audit (application_id, old_status, new_status)
    VALUES (OLD.id, OLD.status, NEW.status);
END IF;
END//
DELIMITER ;