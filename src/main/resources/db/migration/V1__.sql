ALTER TABLE seats
    ADD CONSTRAINT pk_seats PRIMARY KEY (id);

DROP SEQUENCE location_sequence_name CASCADE;

ALTER TABLE tickets
    ALTER COLUMN account_id SET NOT NULL;

ALTER TABLE accounts
    ALTER COLUMN password SET NOT NULL;