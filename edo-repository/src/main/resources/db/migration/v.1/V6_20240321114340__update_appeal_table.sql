ALTER TABLE appeal ADD COLUMN IF NOT EXISTS registration_date TIMESTAMP WITH TIME ZONE;
COMMENT ON COLUMN appeal.registration_date IS 'Дата регистрации обращения';