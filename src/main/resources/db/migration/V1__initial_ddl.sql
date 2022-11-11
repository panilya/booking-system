create table client(
    id              bigserial not null,
    name            varchar(255) not null,
    phone_number    varchar(255) not null,
    email           varchar(255),
    primary key (id)
);

create table restaurant(
    id              bigserial not null,
    name            text not null,
    opening_hours   text,
    primary key (id)
);

create table tables(
    id              bigserial not null,
    number          int not null,
    capacity        smallint not null,
    special_event   text,
    notes           text,
    table_status    varchar(255),
    reserved        boolean,
    placed          timestamp,
    client_id       bigint,
    restaurant_id   bigint not null,
    time_slot_id    bigint,
    primary key (id)
);

create table time_slot(
    id bigserial not null,
    timeslot timestamp not null,
    primary key (id)
);

ALTER TABLE tables
    ADD CONSTRAINT FK_tables_client FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE tables
    ADD CONSTRAINT FK_tables_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE tables
    ADD CONSTRAINT FK_tables_timeslot FOREIGN KEY (time_slot_id) REFERENCES time_slot (id);