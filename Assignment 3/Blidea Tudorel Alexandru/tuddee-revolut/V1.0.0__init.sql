CREATE TABLE `card_types` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(16) NOT NULL,
   `description` text NOT NULL,
   `maxWithdrawal` int(11) NOT NULL,
   `cashbackPercent` float NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `deposit_types` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(64) NOT NULL,
   `description` text NOT NULL,
   `interestRate` float NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `users` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `noSqlId` char(24) NOT NULL,
   `firstName` varchar(64) NOT NULL,
   `lastName` varchar(64) NOT NULL,
   `username` varchar(16) NOT NULL,
   `passwordHash` char(64) NOT NULL,
   `email` varchar(64) NOT NULL,
   `role` varchar(16) NOT NULL,
   `birthDate` date NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `noSqlId` (`noSqlId`),
   UNIQUE KEY `username` (`username`),
   UNIQUE KEY `email` (`email`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `cards` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `ownerId` int(11) NOT NULL,
   `cardTypeId` int(11) NOT NULL,
   `currentAmount` float NOT NULL,
   `cvv` int(3) DEFAULT NULL,
   `number` int(16) NOT NULL,
   `expirationDate` date NOT NULL,
   PRIMARY KEY (`id`),
   KEY `cardTypeId` (`cardTypeId`),
   KEY `ownerId` (`ownerId`),
   CONSTRAINT `cards_ibfk_1` FOREIGN KEY (`cardTypeId`) REFERENCES `card_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `cards_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `deposits` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `ownerId` int(11) NOT NULL,
   `depositTypeId` int(11) NOT NULL,
   `createdOn` date NOT NULL,
   `currentAmount` float NOT NULL,
   `targetDate` date NOT NULL,
   `targetAmount` float NOT NULL,
   PRIMARY KEY (`id`),
   KEY `ownerId` (`ownerId`),
   KEY `depositTypeId` (`depositTypeId`),
   CONSTRAINT `deposits_ibfk_1` FOREIGN KEY (`depositTypeId`) REFERENCES `deposit_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `deposits_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4


CREATE TABLE `interbanking_details` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `bankName` varchar(64) NOT NULL,
   `ibanPrefix` varchar(64) NOT NULL,
   `feesToTransfer` float NOT NULL,
   `endpointToCall` varchar(128) NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4


CREATE TABLE `businesses` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(64) NOT NULL,
   `CIF` int(12) NOT NULL,
   `balance` float NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`),
   UNIQUE KEY `CIF` (`CIF`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `expenses` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(64) NOT NULL,
   `expectedAmount` float NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4


CREATE TABLE `expenses_on_month` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `ownerId` int(11) NOT NULL,
   `expenseId` int(11) NOT NULL,
   `onDate` date NOT NULL,
   `currentValue` float NOT NULL,
   PRIMARY KEY (`id`),
   KEY `ownerId` (`ownerId`,`expenseId`),
   KEY `expenseId` (`expenseId`),
   CONSTRAINT `expenses_on_month_ibfk_1` FOREIGN KEY (`expenseId`) REFERENCES `expenses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `expenses_on_month_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `businesses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
