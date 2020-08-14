CREATE TABLE `config` (
  `key` varchar(64) NOT NULL,
  `value` varchar(64) NOT NULL,
  PRIMARY KEY (`key`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `goods` (
  `article` bigint NOT NULL,
  `variety` varchar(255) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `price` decimal(8,2) NOT NULL,
  `instock` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`article`),
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `persons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `orders` (
  `orderNumber` int NOT NULL AUTO_INCREMENT,
  `orderDate` datetime NOT NULL,
  `person` int NOT NULL,
  `discount` int NOT NULL,
  `orderStatus` enum('PREPARING','SHIPPED','CANCELED') NOT NULL DEFAULT 'PREPARING',
  PRIMARY KEY (`orderNumber`),
  KEY `person_idx` (`person`),
  CONSTRAINT `pers` FOREIGN KEY (`person`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `ordereditems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderNumber` int NOT NULL,
  `article` bigint NOT NULL,
  `variety` varchar(255) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `fixedPrice` decimal(8,2) NOT NULL,
  `orderedQuantity` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderNumber_idx` (`orderNumber`),
  KEY `article_idx` (`article`),
  CONSTRAINT `article` FOREIGN KEY (`article`) REFERENCES `goods` (`article`),
  CONSTRAINT `orderNumber` FOREIGN KEY (`orderNumber`) REFERENCES `orders` (`orderNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci