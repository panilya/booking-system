CREATE TABLE calendar
(
    id bigserial NOT NULL,
    object_id varchar(255) NOT NULL,
    date date NOT NUll,
    availability_status varchar(255) NOT NULL,
    timeslot_id bigint NOT NULL,
    restaurant_id bigint NOT NULL,
    primary key (id)
);

ALTER TABLE time_slot
    ALTER COLUMN timeslot TYPE TIME WITHOUT TIME ZONE USING (timeslot::TIME WITHOUT TIME ZONE);

ALTER TABLE calendar
    ADD CONSTRAINT FK_CALENDAR_ON_TIMESLOT FOREIGN KEY (timeslot_id) REFERENCES time_slot (id);

ALTER TABLE calendar
    ADD CONSTRAINT FK_CALENDAR_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE time_slot
    DROP COLUMN restaurant_id;

ALTER TABLE reservation
    ADD calendar_id BIGINT;

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_CALENDAR FOREIGN KEY (calendar_id) REFERENCES calendar (id);