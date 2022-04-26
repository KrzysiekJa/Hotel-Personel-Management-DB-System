-- MySQL dump 10.13  Distrib 8.0.15, for macos10.14 (x86_64)
--
-- Host: localhost    Database: Hotel-Personel-Management
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Employees`
--

DROP TABLE IF EXISTS `Employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Employees` (
  `employees_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(100) COLLATE utf8_polish_ci NOT NULL,
  `surname` char(100) COLLATE utf8_polish_ci NOT NULL,
  `adress` char(200) COLLATE utf8_polish_ci NOT NULL,
  `sex` char(1) COLLATE utf8_polish_ci NOT NULL,
  `date_of_birth` date NOT NULL,
  `telephone` int(11) NOT NULL,
  `email` char(200) COLLATE utf8_polish_ci DEFAULT NULL,
  `number_of_vacation_days` int(11) NOT NULL,
  `date_of_employment` date NOT NULL,
  PRIMARY KEY (`employees_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employees`
--

LOCK TABLES `Employees` WRITE;
/*!40000 ALTER TABLE `Employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `Employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeesSkills`
--

DROP TABLE IF EXISTS `EmployeesSkills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `EmployeesSkills` (
  `employees_skills_ID` int(11) NOT NULL AUTO_INCREMENT,
  `employees_ID` int(11) NOT NULL,
  `skills_ID` int(11) NOT NULL,
  PRIMARY KEY (`employees_skills_ID`),
  KEY `employees_ID` (`employees_ID`),
  KEY `skills_ID` (`skills_ID`),
  CONSTRAINT `employeesskills_ibfk_1` FOREIGN KEY (`employees_ID`) REFERENCES `employees` (`employees_ID`),
  CONSTRAINT `employeesskills_ibfk_2` FOREIGN KEY (`skills_ID`) REFERENCES `skills` (`skills_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeesSkills`
--

LOCK TABLES `EmployeesSkills` WRITE;
/*!40000 ALTER TABLE `EmployeesSkills` DISABLE KEYS */;
/*!40000 ALTER TABLE `EmployeesSkills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hotels`
--

DROP TABLE IF EXISTS `Hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Hotels` (
  `hotel_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) COLLATE utf8_polish_ci NOT NULL,
  `adress` char(200) COLLATE utf8_polish_ci NOT NULL,
  `telephone` int(11) DEFAULT NULL,
  `email` char(40) COLLATE utf8_polish_ci DEFAULT NULL,
  `standard` char(40) COLLATE utf8_polish_ci NOT NULL,
  `rooms_number` int(11) NOT NULL,
  `creation_date` date NOT NULL,
  PRIMARY KEY (`hotel_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotels`
--

LOCK TABLES `Hotels` WRITE;
/*!40000 ALTER TABLE `Hotels` DISABLE KEYS */;
/*!40000 ALTER TABLE `Hotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HotelsEmployees`
--

DROP TABLE IF EXISTS `HotelsEmployees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `HotelsEmployees` (
  `hotel_employee_ID` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_ID` int(11) NOT NULL,
  `employees_ID` int(11) NOT NULL,
  PRIMARY KEY (`hotel_employee_ID`),
  KEY `hotel_ID` (`hotel_ID`),
  KEY `employees_ID` (`employees_ID`),
  CONSTRAINT `hotelsemployees_ibfk_1` FOREIGN KEY (`hotel_ID`) REFERENCES `hotels` (`hotel_ID`),
  CONSTRAINT `hotelsemployees_ibfk_2` FOREIGN KEY (`employees_ID`) REFERENCES `employees` (`employees_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HotelsEmployees`
--

LOCK TABLES `HotelsEmployees` WRITE;
/*!40000 ALTER TABLE `HotelsEmployees` DISABLE KEYS */;
/*!40000 ALTER TABLE `HotelsEmployees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Position`
--

DROP TABLE IF EXISTS `Position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Position` (
  `position_ID` int(11) NOT NULL AUTO_INCREMENT,
  `employees_ID` int(11) NOT NULL,
  `name` char(100) COLLATE utf8_polish_ci NOT NULL,
  `description` char(200) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`position_ID`),
  KEY `employees_ID` (`employees_ID`),
  CONSTRAINT `position_ibfk_1` FOREIGN KEY (`employees_ID`) REFERENCES `employees` (`employees_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Position`
--

LOCK TABLES `Position` WRITE;
/*!40000 ALTER TABLE `Position` DISABLE KEYS */;
/*!40000 ALTER TABLE `Position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Skills`
--

DROP TABLE IF EXISTS `Skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Skills` (
  `skills_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(100) COLLATE utf8_polish_ci NOT NULL,
  `description` char(200) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`skills_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Skills`
--

LOCK TABLES `Skills` WRITE;
/*!40000 ALTER TABLE `Skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `Skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkPlan`
--

DROP TABLE IF EXISTS `WorkPlan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `WorkPlan` (
  `shift_id` int(11) NOT NULL,
  `hotel_employee_id` int(11) NOT NULL,
  `starting_date` datetime NOT NULL,
  `ending_date` datetime NOT NULL,
  `status` char(50) COLLATE utf8_polish_ci NOT NULL,
  `last_edition_date` date DEFAULT NULL,
  PRIMARY KEY (`shift_id`),
  KEY `hotel_employee_id` (`hotel_employee_id`),
  CONSTRAINT `workplan_ibfk_1` FOREIGN KEY (`hotel_employee_id`) REFERENCES `employees` (`employees_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorkPlan`
--

LOCK TABLES `WorkPlan` WRITE;
/*!40000 ALTER TABLE `WorkPlan` DISABLE KEYS */;
/*!40000 ALTER TABLE `WorkPlan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'Hotel-Personel-Management'
--

--
-- Dumping routines for database 'Hotel-Personel-Management'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_employee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
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
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_hotel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
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
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_position` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
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
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_shift` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
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
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_skill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_skill`(
	IN `name` CHAR(100),
	IN `description` CHAR(200))
BEGIN
	INSERT INTO `Skills` 
    VALUES( NULL, name, description);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_employee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_employee`(
	IN `name` CHAR(100),
	IN `surname` CHAR(100))
BEGIN
	DELETE FROM `Employees`
	WHERE Employees.name = name AND Employees.surname = surname;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_hotel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_hotel`(
	IN `name` CHAR(200))
BEGIN
	DELETE FROM `Hotels`
	WHERE Hotels.name = name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_position` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_position`(
	IN `name` CHAR(100))
BEGIN
	DELETE FROM `Position`
	WHERE Position.name = name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_shift` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_shift`(
	IN `shift_id` INT)
BEGIN
	DELETE FROM `WorkPlan`
	WHERE WorkPlan.shift_id = shift_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_skill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_skill`(
	IN `name` CHAR(100))
BEGIN
	DELETE FROM `Skills`
	WHERE Skills.name = name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-26  1:23:28
=======
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
	`sex` CHAR(40) NOT NULL,
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

CREATE TABLE IF NOT EXISTS `Positions` (
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
	`shift_id` INT NOT NULL AUTO_INCREMENT,
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
