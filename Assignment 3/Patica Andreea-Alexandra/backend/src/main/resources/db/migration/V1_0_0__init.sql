CREATE TABLE `account_type` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(64) NOT NULL,
   `description` text NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `roles` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(64) NOT NULL,
   `description` text NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `users` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `first_name` varchar(64) NOT NULL,
   `last_name` varchar(64) NOT NULL,
   `username` varchar(64) NOT NULL,
   `email` varchar(64) NOT NULL,
   `password` varchar(64) NOT NULL,
   `phone_number` varchar(13) NOT NULL,
   `account_type_id` int(11) NOT NULL,
   `no_sql_id` int(11) DEFAULT NULL,
   `role_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `username` (`username`),
   UNIQUE KEY `email` (`email`),
   KEY `account_type_id` (`account_type_id`),
   KEY `role_id` (`role_id`),
   CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `users_ibfk_2` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `property` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `owner_id` int(11) NOT NULL,
   `name` varchar(64) NOT NULL,
   `address` text NOT NULL,
   `pictures_id` int(11) NOT NULL,
   `price` float NOT NULL,
   `rating` float NOT NULL,
   `description` text NOT NULL,
   `number_of_rooms` int(11) NOT NULL,
   `number_of_beds` int(11) NOT NULL,
   `number_of_bathrooms` int(11) NOT NULL,
   `kitchen` int(11) NOT NULL,
   `picture` blob DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`),
   UNIQUE KEY `pictures_id` (`pictures_id`),
   KEY `owner_id` (`owner_id`),
   CONSTRAINT `property_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `book` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL,
   `property_id` int(11) NOT NULL,
   `date` date NOT NULL,
   `no_sql_id` varchar(64),
   PRIMARY KEY (`id`),
   UNIQUE KEY `no_sql_id` (`no_sql_id`),
   KEY `user_id` (`user_id`),
   KEY `owner_id` (`property_id`),
   CONSTRAINT `book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `book_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;