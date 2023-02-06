ALTER TABLE if exists resolution
    ADD COLUMN is_draft boolean default 'FALSE',
    ADD COLUMN task varchar(255);


comment ON COLUMN resolution.is_draft is 'Черновик';
comment ON COLUMN resolution.task is 'Задача';