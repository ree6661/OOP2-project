-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: deliverydb
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `id_category` int DEFAULT NULL,
  `id_office_sender` int DEFAULT NULL,
  `id_office_recipient` int DEFAULT NULL,
  `id_customer_sender` int DEFAULT NULL,
  `id_customer_recipient` int DEFAULT NULL,
  `id_courier` int DEFAULT NULL,
  `id_status` int DEFAULT NULL,
  `fragile` tinyint(1) DEFAULT NULL,
  `paid` tinyint(1) DEFAULT NULL,
  `cash_on_delivery` double DEFAULT NULL,
  `delivery_to_address` tinyint(1) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `acceptance_by_sender` datetime DEFAULT NULL,
  `customer_delivery` datetime DEFAULT NULL,
  PRIMARY KEY (`id_order`),
  KEY `id_category` (`id_category`),
  KEY `id_office_sender` (`id_office_sender`),
  KEY `id_office_recipient` (`id_office_recipient`),
  KEY `id_customer_sender` (`id_customer_sender`),
  KEY `id_customer_recipient` (`id_customer_recipient`),
  KEY `id_courier` (`id_courier`),
  KEY `id_status` (`id_status`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id_category`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`id_office_sender`) REFERENCES `office` (`id_office`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`id_office_recipient`) REFERENCES `office` (`id_office`),
  CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`id_customer_sender`) REFERENCES `customers` (`id_customer`),
  CONSTRAINT `orders_ibfk_5` FOREIGN KEY (`id_customer_recipient`) REFERENCES `customers` (`id_customer`),
  CONSTRAINT `orders_ibfk_6` FOREIGN KEY (`id_courier`) REFERENCES `couriers` (`id_courier`),
  CONSTRAINT `orders_ibfk_7` FOREIGN KEY (`id_status`) REFERENCES `statuses` (`id_status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,4,1,2,1,1,1,0,25,NULL,NULL,'2021-12-21 00:00:00','2021-12-30 00:00:00'),(2,1,1,4,1,2,1,1,1,1,25,NULL,NULL,'2021-12-27 00:00:00','2021-12-31 00:00:00'),(3,1,1,4,1,2,1,1,1,1,49.99,1,'Ivan rilski 23','2021-12-27 00:00:00','2021-12-30 00:00:00');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-28 22:57:37
