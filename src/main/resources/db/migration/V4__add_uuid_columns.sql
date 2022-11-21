ALTER TABLE room
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();

ALTER TABLE restaurant
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();

ALTER TABLE tables
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();

ALTER TABLE client
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();

ALTER TABLE reservation
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();

ALTER TABLE time_slot
    ADD object_id varchar(255) NOT NULL default gen_random_uuid();