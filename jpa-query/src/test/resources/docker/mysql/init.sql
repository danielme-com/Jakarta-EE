DELIMITER //
CREATE PROCEDURE sp_count_expenses(IN concept VARCHAR(50), OUT total INT)
BEGIN
    SELECT COUNT(e.id) INTO total FROM expenses e WHERE e.concept like CONCAT('%', concept, '%');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_cheap_expenses(IN maxAmount FLOAT)
BEGIN
    SELECT * FROM expenses WHERE amount < maxAmount ORDER BY date DESC;
END //
DELIMITER ;