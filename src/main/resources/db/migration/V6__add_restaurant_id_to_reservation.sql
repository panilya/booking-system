ALTER TABLE reservation
    ADD restaurant_id BIGINT NOT NULL default 1;

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);