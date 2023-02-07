ALTER TABLE if exists resolution
    ADD COLUMN is_draft boolean default 'FALSE',
    ADD COLUMN task varchar(255),
    add question_id  bigint references resolution (id);

comment ON COLUMN resolution.is_draft is 'Черновик';
comment ON COLUMN resolution.task is 'Задача';
comment on column resolution.question_id is 'Вопросы';