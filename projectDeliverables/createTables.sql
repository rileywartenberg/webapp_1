CREATE TABLE `creditCard` (
  `ccnum` int NOT NULL AUTO_INCREMENT,
  `ccType` enum('Visa','MC','AmericanExpress','Discover') DEFAULT NULL,
  `creditLimit` float DEFAULT NULL,
  `balance` float DEFAULT '0',
  `active` int DEFAULT '0',
  PRIMARY KEY (`ccnum`),
  CONSTRAINT `creditCard_chk_1` CHECK ((`balance` <= `creditLimit`))
)

CREATE TABLE `customer` (
  `ssn` bigint NOT NULL,
  `cid` int NOT NULL AUTO_INCREMENT,
  `lastName` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phoneNumber` bigint DEFAULT NULL,
  PRIMARY KEY (`ssn`),
  KEY `cid` (`cid`),
  CONSTRAINT `customer_chk_1` CHECK ((`ssn` < 999999999)),
  CONSTRAINT `customer_chk_2` CHECK ((`phoneNumber` < 9999999999))
)

CREATE TABLE `ownership` (
  `cid` int NOT NULL,
  `ccnum` int NOT NULL,
  `current` int DEFAULT '0',
  PRIMARY KEY (`cid`,`ccnum`),
  KEY `unique_ccn` (`ccnum`),
  CONSTRAINT `unique_ccn` FOREIGN KEY (`ccnum`) REFERENCES `creditCard` (`ccnum`),
  CONSTRAINT `unique_id` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`)
)

CREATE TABLE `Reservations` (
  `Cid` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `room` char(3) DEFAULT NULL,
  `checkIn` date DEFAULT NULL,
  `checkout` date DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `adults` int DEFAULT NULL,
  `kids` int DEFAULT NULL,
  `ccnum` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room` (`room`,`checkIn`),
  KEY `Cid` (`Cid`),
  CONSTRAINT `Reservations_ibfk_1` FOREIGN KEY (`Cid`) REFERENCES `customer` (`cid`),
  CONSTRAINT `Reservations_ibfk_2` FOREIGN KEY (`room`) REFERENCES `rooms` (`roomId`)
)

CREATE TABLE `rooms` (
  `roomId` char(3) NOT NULL,
  `roomName` varchar(100) DEFAULT NULL,
  `beds` int DEFAULT NULL,
  `bedType` varchar(100) DEFAULT NULL,
  `maxOccupancy` int DEFAULT NULL,
  `basePrice` float DEFAULT NULL,
  `decor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`roomId`),
  UNIQUE KEY `roomName` (`roomName`)
)

CREATE TABLE `Users` (
  `name` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `cid` int NOT NULL,
  PRIMARY KEY (`name`)
)
