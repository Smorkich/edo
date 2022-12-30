ALTER TABLE address ADD COLUMN longitude VARCHAR NOT NULL;
ALTER TABLE address ADD COLUMN latitude VARCHAR NOT NULL;
COMMENT ON COLUMN address.longitude IS 'долгота';
COMMENT ON COLUMN address.latitude IS 'широта';