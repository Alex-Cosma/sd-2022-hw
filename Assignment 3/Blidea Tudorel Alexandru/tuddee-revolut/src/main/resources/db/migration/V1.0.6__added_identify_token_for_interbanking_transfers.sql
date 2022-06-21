ALTER TABLE `revolut`.`interbanking_details`
    ADD COLUMN `identifyToken` VARCHAR(64) NOT NULL AFTER `endpointToCall`;
