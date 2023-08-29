CREATE TABLE IF NOT EXISTS notification_settings
(
    id                SERIAL PRIMARY KEY,
    activation_status VARCHAR(100)
);

ALTER TABLE IF EXISTS employee
    ADD COLUMN notification_settings_id BIGINT,
    ADD CONSTRAINT employee_notification_settings_fk FOREIGN KEY (notification_settings_id) REFERENCES notification_settings (id);