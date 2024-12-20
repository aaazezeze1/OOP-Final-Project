CREATE DATABASE  IF NOT EXISTS `hbsys_schema` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hbsys_schema`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hbsys_schema
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `billing`
--

DROP TABLE IF EXISTS `billing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billing` (
  `billing_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `roomfee_id` int NOT NULL,
  `consultation_id` int NOT NULL,
  `diagnostic_id` int NOT NULL,
  `therapeutic_id` int NOT NULL,
  `medication_id` int NOT NULL,
  `total_bill` double NOT NULL,
  PRIMARY KEY (`billing_id`),
  KEY `fk_patient_id` (`patient_id`),
  KEY `fk_consultation` (`consultation_id`),
  KEY `fk_diagnostic` (`diagnostic_id`),
  KEY `fk_medication` (`medication_id`),
  KEY `fk_roomfee` (`roomfee_id`),
  KEY `fk_therapeutic` (`therapeutic_id`),
  CONSTRAINT `fk_consultation` FOREIGN KEY (`consultation_id`) REFERENCES `consultationfee` (`consultation_id`),
  CONSTRAINT `fk_diagnostic` FOREIGN KEY (`diagnostic_id`) REFERENCES `diagnosticprocedure` (`diagnostic_id`),
  CONSTRAINT `fk_medication` FOREIGN KEY (`medication_id`) REFERENCES `medicationfee` (`medication_id`),
  CONSTRAINT `fk_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_roomfee` FOREIGN KEY (`roomfee_id`) REFERENCES `roomfee` (`room_id`),
  CONSTRAINT `fk_therapeutic` FOREIGN KEY (`therapeutic_id`) REFERENCES `therapeuticprocedure` (`therapeutic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billing`
--

LOCK TABLES `billing` WRITE;
/*!40000 ALTER TABLE `billing` DISABLE KEYS */;
INSERT INTO `billing` VALUES (1,1,1,1,1,1,1,23500),(2,2,2,2,2,2,2,13100),(3,3,3,3,3,3,3,2060),(4,4,4,4,4,4,4,32250),(5,5,5,5,5,5,5,10500),(6,6,6,6,6,6,6,41300),(7,7,7,7,7,7,7,27800),(8,8,8,8,8,8,8,1500),(9,9,9,9,9,9,9,35300),(10,11,11,10,10,10,10,11400);
/*!40000 ALTER TABLE `billing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultationfee`
--

DROP TABLE IF EXISTS `consultationfee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultationfee` (
  `patient_id` int NOT NULL,
  `consultation_id` int NOT NULL AUTO_INCREMENT,
  `consultation_price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`consultation_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `consultationfee_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultationfee`
--

LOCK TABLES `consultationfee` WRITE;
/*!40000 ALTER TABLE `consultationfee` DISABLE KEYS */;
INSERT INTO `consultationfee` VALUES (1,1,500),(2,2,500),(3,3,500),(4,4,500),(5,5,0),(6,6,500),(7,7,500),(8,8,0),(9,9,0),(11,10,500);
/*!40000 ALTER TABLE `consultationfee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagnosticprocedure`
--

DROP TABLE IF EXISTS `diagnosticprocedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosticprocedure` (
  `patient_id` int NOT NULL,
  `diagnostic_id` int NOT NULL AUTO_INCREMENT,
  `diagnostic_name` varchar(45) NOT NULL,
  `diagnostic_fee` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`diagnostic_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `diagnosticprocedure_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosticprocedure`
--

LOCK TABLES `diagnosticprocedure` WRITE;
/*!40000 ALTER TABLE `diagnosticprocedure` DISABLE KEYS */;
INSERT INTO `diagnosticprocedure` VALUES (1,1,'CT Scan',1200),(2,2,'Mammography',200),(3,3,'None',0),(4,4,'Electrocardiogram (ECG)',250),(5,5,'Blood Test',500),(6,6,'Chest X-ray',350),(7,7,'Urinalysis',100),(8,8,'None',0),(9,9,'None',0),(11,10,'Electrocardiogram (ECG)',250);
/*!40000 ALTER TABLE `diagnosticprocedure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounts` (
  `discounts_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `insurance` decimal(5,2) DEFAULT '0.00',
  `pwd` decimal(5,2) DEFAULT '0.00',
  `senior_citizen` decimal(5,2) DEFAULT '0.00',
  `total_discount` decimal(5,2) DEFAULT '0.00',
  PRIMARY KEY (`discounts_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `discounts_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES (1,1,0.20,0.15,0.00,0.35),(2,2,0.00,0.00,0.10,0.10),(3,3,0.20,0.00,0.00,0.20),(4,4,0.00,0.15,0.00,0.15),(5,5,0.00,0.00,0.10,0.10),(6,6,0.20,0.00,0.00,0.20),(7,7,0.20,0.15,0.10,0.45),(8,8,0.00,0.00,0.00,0.00),(9,9,0.20,0.15,0.10,0.45),(10,10,0.00,0.00,0.00,0.00),(11,11,0.20,0.00,0.00,0.20);
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `final_billing`
--

DROP TABLE IF EXISTS `final_billing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `final_billing` (
  `final_billing_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `billing_id` int NOT NULL,
  `discounts_id` int NOT NULL,
  `final_bill` double NOT NULL,
  `payment_status` tinyint(1) DEFAULT '0',
  `amount_paid` double NOT NULL DEFAULT '0',
  `change_amount` double DEFAULT '0',
  `remaining_balance` double DEFAULT '0',
  PRIMARY KEY (`final_billing_id`),
  KEY `patient_id` (`patient_id`),
  KEY `billing_id` (`billing_id`),
  KEY `fk_discounts` (`discounts_id`),
  CONSTRAINT `final_billing_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `final_billing_ibfk_2` FOREIGN KEY (`billing_id`) REFERENCES `billing` (`billing_id`),
  CONSTRAINT `fk_discounts` FOREIGN KEY (`discounts_id`) REFERENCES `discounts` (`discounts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_billing`
--

LOCK TABLES `final_billing` WRITE;
/*!40000 ALTER TABLE `final_billing` DISABLE KEYS */;
INSERT INTO `final_billing` VALUES (1,1,1,1,15275,1,15300,0,0),(2,2,2,2,11790,0,0,0,0),(3,3,3,3,1648,1,2000,0,0),(4,4,4,4,27412.5,1,27500,0,0),(5,5,5,5,9450,0,0,0,0),(6,6,6,6,33040,1,33050,0,0),(7,7,7,7,15290,0,0,0,0),(8,8,8,8,1500,0,0,0,0),(9,9,9,9,19415,0,0,0,0),(10,11,10,11,9120,0,0,0,0);
/*!40000 ALTER TABLE `final_billing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicationfee`
--

DROP TABLE IF EXISTS `medicationfee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicationfee` (
  `patient_id` int NOT NULL,
  `medication_id` int NOT NULL AUTO_INCREMENT,
  `medicine_name` varchar(50) NOT NULL,
  `quantity` int NOT NULL,
  `total_price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`medication_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `medicationfee_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicationfee`
--

LOCK TABLES `medicationfee` WRITE;
/*!40000 ALTER TABLE `medicationfee` DISABLE KEYS */;
INSERT INTO `medicationfee` VALUES (1,1,'Antibacterial',2,800),(2,2,'Immunosuppressives',2,1400),(3,3,'Decongestants',2,360),(4,4,'None',0,0),(5,5,'None',0,0),(6,6,'Antacid',3,450),(7,7,'Hypoglycemics',5,2000),(8,8,'Antihistamine',6,1500),(9,9,'None',0,0),(11,10,'Anti-Anxiety',5,1750);
/*!40000 ALTER TABLE `medicationfee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL,
  `patient_name` varchar(50) NOT NULL,
  `patient_age` int NOT NULL,
  `patient_address` varchar(50) NOT NULL,
  `patient_phonenum` varchar(15) NOT NULL,
  `patient_email` varchar(50) NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'Cyrelle Gapit',45,'San Pablo City','09987861214','cyrelle@gmail.com'),(2,'Amazing cabiles',67,'San Pablo City','09889775643','amaze@gmail.com'),(3,'Francen Manalo',55,'Quezon City','09882345645','fran@gmail.com'),(4,'Ivory Deriquits',12,'Wawa, Laguna','09976755321','ivy@gmail.com'),(5,'Joshua Aguirs',99,'Manila City','09786574324','aguirs@gmail.com'),(6,'Laufey',28,'San Francisco','09889786531','lau@gmail.com'),(7,'Cathy',66,'San Pablo City','09989675423','cathy@gmail.com'),(8,'Britney',55,'United Kingdom','09956342116','brit@gmail.com'),(9,'Riri',78,'Los Angeles','09897644324','riri@gmail.com'),(10,'Alex',55,'San Pablo City','09897656341','alex@gmail.com'),(11,'Sparkling',28,'Korea','09967435117','spark@gmail.com');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomfee`
--

DROP TABLE IF EXISTS `roomfee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomfee` (
  `patient_id` int NOT NULL,
  `room_id` int NOT NULL AUTO_INCREMENT,
  `room_type` varchar(50) NOT NULL,
  `stay_duration` int NOT NULL DEFAULT '0',
  `roomfee_price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`room_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `roomfee_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomfee`
--

LOCK TABLES `roomfee` WRITE;
/*!40000 ALTER TABLE `roomfee` DISABLE KEYS */;
INSERT INTO `roomfee` VALUES (1,1,'Private',4,16000),(2,2,'Ward',5,6000),(3,3,'Ward',1,1200),(4,4,'ICU',6,30000),(5,5,'Semi-Private',5,10000),(6,6,'ICU',7,35000),(7,7,'ICU',5,25000),(8,8,'None',0,0),(9,9,'ICU',7,35000),(10,10,'ICU',8,40000),(11,11,'Ward',7,8400);
/*!40000 ALTER TABLE `roomfee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `therapeuticprocedure`
--

DROP TABLE IF EXISTS `therapeuticprocedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `therapeuticprocedure` (
  `patient_id` int NOT NULL,
  `therapeutic_id` int NOT NULL AUTO_INCREMENT,
  `therapy_name` varchar(45) NOT NULL,
  `therapy_fee` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`therapeutic_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `therapeuticprocedure_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `therapeuticprocedure`
--

LOCK TABLES `therapeuticprocedure` WRITE;
/*!40000 ALTER TABLE `therapeuticprocedure` DISABLE KEYS */;
INSERT INTO `therapeuticprocedure` VALUES (1,1,'Surgery',5000),(2,2,'Surgery',5000),(3,3,'None',0),(4,4,'Chemotherapy',1500),(5,5,'None',0),(6,6,'Surgery',5000),(7,7,'Acupuncture',200),(8,8,'None',0),(9,9,'Spinal Manipulation',300),(11,10,'Physical Therapy',500);
/*!40000 ALTER TABLE `therapeuticprocedure` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-20 12:41:37
