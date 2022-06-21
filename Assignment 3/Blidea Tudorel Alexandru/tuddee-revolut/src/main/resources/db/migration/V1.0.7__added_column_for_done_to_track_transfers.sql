ALTER TABLE `revolut`.`transfers`
    ADD COLUMN `doneOn` DATETIME NULL AFTER `remainingAttempts`;
