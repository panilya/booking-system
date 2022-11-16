CREATE TABLE client
(
    id           BIGSERIAL NOT NULL,
    name         VARCHAR(255),
    phone_number TEXT,
    email        TEXT,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id             BIGSERIAL NOT NULL,
    status         VARCHAR(255),
    payment_status VARCHAR(255),
    party_size     INTEGER                                 NOT NULL,
    table_id       BIGINT,
    client_id      BIGINT,
    time_slot_id   BIGINT,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

CREATE TABLE tables
(
    id            BIGSERIAL NOT NULL,
    capacity      INTEGER,
    restaurant_id BIGINT,
    CONSTRAINT pk_table PRIMARY KEY (id)
);

CREATE TABLE time_slot
(
    id       BIGSERIAL NOT NULL ,
    timeslot timestamp,
    CONSTRAINT pk_timeslot PRIMARY KEY (id)
);

CREATE TABLE restaurant
(
    id            BIGSERIAL                               NOT NULL,
    name          TEXT                                    NOT NULL,
    opening_hours TEXT                                    NOT NULL,
    CONSTRAINT pk_restaurant PRIMARY KEY (id)
);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_TABLE FOREIGN KEY (table_id) REFERENCES tables (id);

ALTER TABLE reservation
    ADD CONSTRAINT FK_RESERVATION_ON_TIMESLOT FOREIGN KEY (time_slot_id) REFERENCES time_slot (id);

ALTER TABLE tables
    ADD CONSTRAINT FK_TABLE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);
