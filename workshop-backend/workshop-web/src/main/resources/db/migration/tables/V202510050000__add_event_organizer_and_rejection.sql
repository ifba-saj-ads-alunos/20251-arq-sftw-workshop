ALTER TABLE events
    ADD COLUMN organizer_id BINARY(16) NULL;

ALTER TABLE events
    ADD COLUMN rejection_justification TEXT NULL;
