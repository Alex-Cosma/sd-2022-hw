ALTER TABLE `revolut`.`transfers`
    ADD COLUMN `startedOn` DATETIME NOT NULL AFTER `doneOn`;
