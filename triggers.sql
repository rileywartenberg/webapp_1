DROP TRIGGER IF EXISTS check_valid_dates;

DELIMITER $
CREATE TRIGGER check_valid_dates BEFORE INSERT ON Reservations
	FOR EACH ROW
    BEGIN
		IF NEW.checkIn > NEW.checkOut THEN
        SIGNAL SQLSTATE '12345'
        SET MESSAGE_TEXT = 'Invalid check-in and check-out dates';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_overbook;

DELIMITER $
CREATE TRIGGER check_overbook BEFORE INSERT ON Reservations
	FOR EACH ROW
    BEGIN
		IF 0 < (SELECT COUNT(*) FROM Reservations r
					WHERE NEW.room = r.room AND
							((NEW.checkIn BETWEEN r.checkIn AND r.checkOut) OR
                            (NEW.checkOut BETWEEN r.checkIn AND r.checkOut) OR
                            (NEW.checkIn < r.checkIn AND NEW.checkOut > r.checkOut))) THEN
			SIGNAL SQLSTATE '12345'
			SET MESSAGE_TEXT = 'These dates are unavailable';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_change_overbook;

DELIMITER $
CREATE TRIGGER check_change_overbook BEFORE UPDATE ON Reservations
	FOR EACH ROW
    BEGIN
		IF 0 < (SELECT COUNT(*) FROM Reservations r
					WHERE NEW.id != r.id AND
							NEW.room = r.room AND
							((NEW.checkIn BETWEEN r.checkIn AND r.checkOut) OR
							(NEW.checkOut BETWEEN r.checkIn AND r.checkOut) OR
							(NEW.checkIn < r.checkIn AND NEW.checkOut > r.checkOut))) THEN
			SIGNAL SQLSTATE '12345'
			SET MESSAGE_TEXT = 'These dates are unavailable';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_cc_limit;

DELIMITER $
CREATE TRIGGER check_cc_limit BEFORE INSERT ON Reservations
	FOR EACH ROW
    BEGIN
		SET @currentbal = (SELECT balance FROM creditCard cc
							WHERE cc.ccnum = NEW.ccnum);
		IF @currentbal + (NEW.rate * DATEDIFF(NEW.CheckOut, NEW.CheckIn))
									> (SELECT creditLimit FROM creditCard cc
										WHERE cc.ccnum = NEW.ccnum) THEN
			SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Exceeds credit card limit';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_cc_limit_update;

DELIMITER $
CREATE TRIGGER check_cc_limit_update BEFORE UPDATE ON Reservations
	FOR EACH ROW
    BEGIN
		SET @currentbal = (SELECT balance FROM creditCard cc
							WHERE cc.ccnum = NEW.ccnum);
		SET @refund = (SELECT OLD.rate * DATEDIFF(OLD.CheckOut, OLD.CheckIn) FROM Reservations
						WHERE id = OLD.id);
		IF @currentbal + - @refund + (NEW.rate * DATEDIFF(NEW.CheckOut, NEW.CheckIn))
									> (SELECT creditLimit FROM creditCard cc
										WHERE cc.ccnum = NEW.ccnum) THEN
			SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Exceeds credit card limit';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS charge_creditcard;

DELIMITER $
CREATE TRIGGER charge_creditcard AFTER INSERT ON Reservations
	FOR EACH ROW
    BEGIN
		UPDATE creditCard SET balance = balance + (NEW.rate * DATEDIFF(NEW.CheckOut, NEW.CheckIn))
			WHERE ccnum = NEW.ccnum;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS charge_creditcard_update;

DELIMITER $
CREATE TRIGGER charge_creditcard_update AFTER UPDATE ON Reservations
	FOR EACH ROW
    BEGIN
		SET @refund = OLD.rate * DATEDIFF(OLD.CheckOut, OLD.CheckIn);
		UPDATE creditCard SET balance = balance - @refund + (NEW.rate * DATEDIFF(NEW.CheckOut, NEW.CheckIn))
			WHERE ccnum = NEW.ccnum;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS refund_creditcard;

DELIMITER $
CREATE TRIGGER refund_creditcard AFTER DELETE ON Reservations
	FOR EACH ROW
    BEGIN
		UPDATE creditCard SET balance = balance - (OLD.rate * DATEDIFF(OLD.CheckOut, OLD.CheckIn))
			WHERE ccnum = OLD.ccnum;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_reservation_capacity;

DELIMITER $
CREATE TRIGGER check_reservation_capacity BEFORE INSERT ON Reservations
	FOR EACH ROW
    BEGIN
		IF NEW.adults + NEW.kids > (SELECT maxOccupancy FROM rooms
										WHERE roomId = NEW.room) THEN
			SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Exceeds max occupancy';
		END IF;
	END$
DELIMITER ;

DROP TRIGGER IF EXISTS check_reservation_capacity_update;

DELIMITER $
CREATE TRIGGER check_reservation_capacity_update BEFORE UPDATE ON Reservations
	FOR EACH ROW
    BEGIN
		IF NEW.adults + NEW.kids > (SELECT maxOccupancy FROM rooms
										WHERE roomId = NEW.room) THEN
			SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Exceeds max occupancy';
		END IF;
	END$
DELIMITER ;
