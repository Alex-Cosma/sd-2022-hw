CREATE TABLE `revolut`.`card_status` (
  `id` INT NOT NULL,
  `name` VARCHAR(16) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE card_status
ADD UNIQUE (name);

ALTER TABLE `revolut`.`cards` 
ADD COLUMN `statusId` INT NOT NULL AFTER `expirationDate`;


ALTER TABLE cards ADD FOREIGN KEY (statusId) REFERENCES card_status(id);

