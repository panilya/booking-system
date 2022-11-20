ALTER TABLE reservation
    ADD created_at timestamp NOT NULL default now();