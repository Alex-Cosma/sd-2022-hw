ALTER TABLE `revolut`.`transfers`
    ADD COLUMN `remainingAttempts` SMALLINT NOT NULL DEFAULT 16 AFTER `to`;
