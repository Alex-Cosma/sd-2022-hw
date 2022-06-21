CREATE TABLE `revolut`.`offers`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(64) NOT NULL,
    `description` VARCHAR(256) NULL,
    `available`   TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

ALTER TABLE `revolut`.`card_types`
    ADD COLUMN `color` VARCHAR(16) NOT NULL AFTER `cashbackPercent`;

ALTER TABLE `revolut`.`contacts`
    CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `revolut`.`transfers`
    CHANGE COLUMN `from` `from` CHAR(36) NOT NULL ,
    CHANGE COLUMN `to` `to` CHAR(36) NOT NULL ;

ALTER TABLE `revolut`.`transfers`
    CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;
