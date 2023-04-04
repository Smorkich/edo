ALTER TABLE if EXISTS employee
    ALTER COLUMN external_id TYPE VARCHAR(30),
    DROP COLUMN notification_id;