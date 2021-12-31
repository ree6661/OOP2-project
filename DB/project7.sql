CREATE DATABASE `deliverydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `admins` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phone` char(12) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `categories` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cities` (
  `id_city` int NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_city`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `companies` (
  `id_company` int NOT NULL AUTO_INCREMENT,
  `company` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_company`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `couriers` (
  `id_courier` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phone` char(12) NOT NULL,
  `password` varchar(30) NOT NULL,
  `id_office` int NOT NULL,
  PRIMARY KEY (`id_courier`),
  KEY `couriers_ibfk_1` (`id_office`),
  CONSTRAINT `couriers_ibfk_1` FOREIGN KEY (`id_office`) REFERENCES `office` (`id_office`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customers` (
  `id_customer` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phone` char(12) NOT NULL,
  `password` varchar(30) NOT NULL,
  `id_city` int DEFAULT NULL,
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_customer`),
  KEY `id_city` (`id_city`),
  CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `office` (
  `id_office` int NOT NULL AUTO_INCREMENT,
  `id_company` int DEFAULT NULL,
  `id_city` int DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_office`),
  KEY `id_company` (`id_company`),
  KEY `id_city` (`id_city`),
  CONSTRAINT `office_ibfk_1` FOREIGN KEY (`id_company`) REFERENCES `companies` (`id_company`),
  CONSTRAINT `office_ibfk_2` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

CREATE TABLE `price_list` (
  `id_company` int NOT NULL,
  `id_category` int NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id_company`,`id_category`),
  KEY `id_category` (`id_category`),
  CONSTRAINT `price_list_ibfk_1` FOREIGN KEY (`id_company`) REFERENCES `companies` (`id_company`),
  CONSTRAINT `price_list_ibfk_2` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `statuses` (
  `id_status` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_status`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
