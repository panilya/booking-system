ALTER TABLE time_slot
    ADD restaurant_id BIGINT NOT NULL default 1;

ALTER TABLE time_slot
    ADD CONSTRAINT FK_TIME_SLOT_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);