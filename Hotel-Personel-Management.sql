CREATE DATABASE IF NOT EXISTS `Hotel-Personel-Management` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;
USE `Hotel-Personel-Management`;


CREATE TABLE IF NOT EXISTS `Hotels` (
	`hotel_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(200) NOT NULL,
	`adress` CHAR(200) NOT NULL,
	`telephone` INT,
	`email` CHAR(40),
	`standard` CHAR(40) NOT NULL,
	`rooms_number` INT NOT NULL,
	`creation_date` DATE NOT NULL,
	PRIMARY KEY (`hotel_ID`)
);

CREATE TABLE IF NOT EXISTS `Employees` (
	`employees_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(100) NOT NULL,
	`surname` CHAR(100) NOT NULL,
	`adress` CHAR(200) NOT NULL,
	`sex` CHAR(1) NOT NULL,
	`date_of_birth` DATE NOT NULL,
	`telephone` INT NOT NULL,
	`email` CHAR(200),
	`number_of_vacation_days` INT NOT NULL,
	`date_of_employment` DATE NOT NULL,
	PRIMARY KEY (`employees_ID`)
);

CREATE TABLE IF NOT EXISTS `HotelsEmployees` (
	`hotel_employee_ID` INT NOT NULL AUTO_INCREMENT,
	`hotel_ID` INT NOT NULL,
	`employees_ID` INT NOT NULL,
	PRIMARY KEY (`hotel_employee_ID`),
	FOREIGN KEY (`hotel_ID`) REFERENCES Hotels(`hotel_ID`),
	FOREIGN KEY (`employees_ID`) REFERENCES Employees(`employees_ID`)
);

CREATE TABLE IF NOT EXISTS `Position` (
	`position_ID` INT NOT NULL AUTO_INCREMENT,
	`employees_ID` INT NOT NULL,
	`name` CHAR(100) NOT NULL,
	`description` CHAR(200) NOT NULL,
	PRIMARY KEY (`position_ID`),
	FOREIGN KEY (`employees_ID`) REFERENCES Employees(`employees_ID`)
);

CREATE TABLE IF NOT EXISTS `Skills` (
	`skills_ID` INT NOT NULL AUTO_INCREMENT,
	`name` CHAR(100) NOT NULL,
	`description` CHAR(200) NOT NULL,
	PRIMARY KEY (`skills_ID`)
);

CREATE TABLE IF NOT EXISTS `WorkPlan` (
	`shift_id` INT NOT NULL,
	`hotel_employee_id` INT NOT NULL,
	`starting_date` DATETIME NOT NULL,
	`ending_date` DATETIME NOT NULL,
	`status` CHAR(50) NOT NULL,
	`last_edition_date` DATE,
	PRIMARY KEY (`shift_id`),
	FOREIGN KEY (`hotel_employee_id`) REFERENCES Employees(`employees_ID`)
);

CREATE TABLE IF NOT EXISTS `EmployeesSkills`(
	`employees_skills_ID` INT NOT NULL AUTO_INCREMENT,
	`employees_ID` INT NOT NULL,
	`skills_ID` INT NOT NULL,
	PRIMARY KEY (`employees_skills_ID`),
	FOREIGN KEY (`employees_ID`) REFERENCES Employees(`employees_ID`),
	FOREIGN KEY (`skills_ID`) REFERENCES Skills(`skills_ID`)
);