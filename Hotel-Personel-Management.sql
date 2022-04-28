CREATE DATABASE IF NOT EXISTS `Hotel-Personel-Management` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;
USE `Hotel-Personel-Management`;


DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_employee`(
	IN `name` CHAR(100),
	IN `surname` CHAR(100),
	IN `adress` CHAR(200),
	IN `sex` CHAR(1),
	IN `date_of_birth` DATE ,
	IN `telephone` INT,
	IN `email` CHAR(200),
	IN `number_of_vacation_days` INT,
	IN `date_of_employment` DATE )
BEGIN
	INSERT INTO `Employees` 
    VALUES( NULL, name, surname, adress, sex, date_of_birth, telephone, email, number_of_vacation_days, date_of_employment);
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_hotel`(
	IN `name` CHAR(200),
	IN `adress` CHAR(200),
	IN `telephone` INT,
	IN `email` CHAR(40),
	IN `standard` CHAR(40),
	IN `rooms_number` INT)
BEGIN
	INSERT INTO `Hotels` 
    VALUES( NULL, name, adress, telephone, email, standard, rooms_number, current_date());
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_position`(
	IN `employees_ID` INT,
	IN `name` CHAR(100),
	IN `description` CHAR(200))
BEGIN
	INSERT INTO `Position` 
    VALUES( NULL, name, description);
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_shift`(
	IN `hotel_employee_id` INT,
	IN `starting_date` DATETIME,
	IN `ending_date` DATETIME,
	IN `status` CHAR(50))
BEGIN
	INSERT INTO `WorkPlan` 
    VALUES( NULL, hotel_employee_id, starting_date, ending_date, status, current_date());
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_skill`(
	IN `name` CHAR(100),
	IN `description` CHAR(200))
BEGIN
	INSERT INTO `Skills` 
    VALUES( NULL, name, description);
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_employee`(
	IN `name` CHAR(100),
	IN `surname` CHAR(100))
BEGIN
	DELETE FROM `Employees`
	WHERE Employees.name = name AND Employees.surname = surname;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_hotel`(
	IN `name` CHAR(200))
BEGIN
	DELETE FROM `Hotels`
	WHERE Hotels.name = name;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_position`(
	IN `name` CHAR(100))
BEGIN
	DELETE FROM `Position`
	WHERE Position.name = name;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_shift`(
	IN `shift_id` INT)
BEGIN
	DELETE FROM `WorkPlan`
	WHERE WorkPlan.shift_id = shift_id;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_skill`(
	IN `name` CHAR(100))
BEGIN
	DELETE FROM `Skills`
	WHERE Skills.name = name;
END ;;



CREATE TABLE IF NOT EXISTS `Hotels` (
	`hotel_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(200) NOT NULL,
	`adress` CHAR(200) NOT NULL,
	`telephone` INT,
	`email` CHAR(40),
	`standard` CHAR(40) NOT NULL,
	`rooms_number` INT NOT NULL,
	`creation_date` DATETIME NOT NULL,
	PRIMARY KEY (`hotel_ID`)
);

CREATE TABLE IF NOT EXISTS `Positions` (
	`position_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(100) NOT NULL,
	`description` CHAR(200) NOT NULL,
	PRIMARY KEY (`position_ID`)
);

CREATE TABLE IF NOT EXISTS `Employees` (
	`employee_ID` INT NOT NULL AUTO_INCREMENT,
    `position_ID` INT NOT NULL,
	`name` CHAR(100) NOT NULL,
	`surname` CHAR(100) NOT NULL,
	`adress` CHAR(200) NOT NULL,
	`sex` CHAR(40) NOT NULL,
	`date_of_birth` DATE NOT NULL,
	`telephone` INT NOT NULL,
	`email` CHAR(200),
	`number_of_vacation_days` INT NOT NULL,
	`date_of_employment` DATE NOT NULL,
    `creation_date` DATETIME NOT NULL,
	PRIMARY KEY (`employee_ID`),
    FOREIGN KEY (`position_ID`) REFERENCES Positions(`position_ID`)
);

CREATE TABLE IF NOT EXISTS `HotelsEmployees` (
	`hotel_employee_ID` INT NOT NULL AUTO_INCREMENT,
	`hotel_ID` INT NOT NULL,
	`employee_ID` INT NOT NULL,
	PRIMARY KEY (`hotel_employee_ID`),
	FOREIGN KEY (`hotel_ID`) REFERENCES Hotels(`hotel_ID`),
	FOREIGN KEY (`employee_ID`) REFERENCES Employees(`employee_ID`)
);

CREATE TABLE IF NOT EXISTS `Skills` (
	`skill_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(100) NOT NULL,
	`description` CHAR(200) NOT NULL,
	PRIMARY KEY (`skill_ID`)
);

CREATE TABLE IF NOT EXISTS `WorkPlan` (
	`shift_id` INT NOT NULL AUTO_INCREMENT,
	`hotel_employee_id` INT NOT NULL,
	`starting_date` DATETIME NOT NULL,
	`ending_date` DATETIME NOT NULL,
	`status` CHAR(50) NOT NULL,
    `creation_date` DATETIME NOT NULL,
	`last_edition_date` DATETIME,
	PRIMARY KEY (`shift_id`),
	FOREIGN KEY (`hotel_employee_id`) REFERENCES HotelsEmployees(`hotel_employee_ID`)
);

CREATE TABLE IF NOT EXISTS `EmployeesSkills`(
	`employee_skill_ID` INT NOT NULL AUTO_INCREMENT,
	`employee_ID` INT NOT NULL,
	`skill_ID` INT NOT NULL,
	PRIMARY KEY (`employee_skill_ID`),
	FOREIGN KEY (`employee_ID`) REFERENCES Employees(`employee_ID`),
	FOREIGN KEY (`skill_ID`) REFERENCES Skills(`skill_ID`)
);
