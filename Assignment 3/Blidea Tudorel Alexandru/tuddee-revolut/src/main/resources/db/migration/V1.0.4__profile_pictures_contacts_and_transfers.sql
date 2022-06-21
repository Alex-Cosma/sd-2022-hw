ALTER TABLE `revolut`.`users`
    ADD COLUMN `profileImageName` CHAR(70) NULL AFTER `birthDate`;

ALTER TABLE `revolut`.`cards`
    ADD COLUMN `iban` CHAR(36) NOT NULL AFTER `statusId`,
    ADD UNIQUE INDEX `iban_UNIQUE` (`iban` ASC) VISIBLE;
;


CREATE TABLE `revolut`.`contacts`
(
    `id`            INT         NOT NULL,
    `person1Id`     INT         NOT NULL,
    `person2Id`     INT         NOT NULL,
    `requestStatus` VARCHAR(16) NOT NULL,
    `acceptedOn`    DATETIME    NULL,
    PRIMARY KEY (`id`),
    INDEX `person1_fk_idx` (`person1Id` ASC) VISIBLE,
    INDEX `person2_fk_users_idx` (`person2Id` ASC) VISIBLE,
    CONSTRAINT `person1_fk_users`
        FOREIGN KEY (`person1Id`)
            REFERENCES `revolut`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `person2_fk_users`
        FOREIGN KEY (`person2Id`)
            REFERENCES `revolut`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `revolut`.`transfers`
(
    `id`             INT         NOT NULL,
    `transferStatus` VARCHAR(16) NOT NULL,
    `amount`         DOUBLE      NOT NULL,
    `from`           CHAR(34)    NOT NULL,
    `to`             CHAR(34)    NOT NULL,
    PRIMARY KEY (`id`)
);
