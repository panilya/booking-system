CREATE TABLE client
(
    id bigserial not null,
    name varchar(255) not null,
    phone_number text,
    email text,
    primary key (id)
);

CREATE TABLE tables
(
    id bigserial not null,
    capacity smallint not null,
    availability boolean not null,
    placed timestamp,
    booking_for timestamp,
    special_event text,
    notes text,
    client_status varchar(255),
    client_id bigint,
    restaurant_id bigint,
    primary key (id)
);

CREATE TABLE restaurant
(
    id bigserial not null,
    name text not null,
    opening_hours text not null,
    table_id bigint not null,
    primary key (id)
);

ALTER TABLE tables
    ADD CONSTRAINT FK_dwad321tgesdawgrdgrd FOREIGN KEY (client_id) REFERENCES client (id);
ALTER TABLE tables
    ADD CONSTRAINT FK_321dawffgrdhth3543 FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);