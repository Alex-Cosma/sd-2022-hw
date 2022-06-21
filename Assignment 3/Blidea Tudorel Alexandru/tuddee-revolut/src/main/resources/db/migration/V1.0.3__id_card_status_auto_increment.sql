ALTER TABLE cards
    DROP FOREIGN KEY cards_ibfk_3;

alter table card_status MODIFY id int NOT NULL AUTO_INCREMENT;

ALTER TABLE cards ADD FOREIGN KEY (statusId) REFERENCES card_status(id);