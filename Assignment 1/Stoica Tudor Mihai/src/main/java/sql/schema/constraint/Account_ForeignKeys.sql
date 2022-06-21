ALTER TABLE `Account` ADD CONSTRAINT `Account_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `Client`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `Account` ADD CONSTRAINT `Account_ibfk_2` FOREIGN KEY (`currency_id`) REFERENCES `Currency`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;