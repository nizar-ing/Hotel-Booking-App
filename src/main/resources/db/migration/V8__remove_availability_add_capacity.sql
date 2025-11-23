ALTER TABLE rooms
    DROP COLUMN availability;

ALTER TABLE rooms
    ADD COLUMN capacity INT NOT NULL DEFAULT 1;