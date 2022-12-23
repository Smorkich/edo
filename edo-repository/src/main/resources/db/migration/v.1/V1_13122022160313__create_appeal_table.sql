create table if not exists appeal
(
    id                     bigserial not null primary key,
    creation_date          timestamp with time zone,
    archived_date          timestamp with time zone,
    number                 varchar(255),
    annotation             varchar(255),
    creator_id             bigint references edo.employee (id),

    appeals_status         varchar(255),
    appeals_receipt_method varchar(255),
    appeal_authors         varchar(255),
    appeal_filepool        varchar(255),
    appeal_question        varchar(255),
    nomenclature_id        bigint references edo.nomenclature (id),
    resolution_id          bigint references edo.resolution (id)
    --thema_id               bigint references edo.thema (id)
);
comment on table appeal
    is 'Обращение';
comment on column appeal.creation_date
    is 'Дата создания обращения';
comment on column appeal.archived_date
    is 'Дата отработанного обращения';
comment on column appeal.number
    is 'Номер обращения';
comment on column appeal.annotation
    is 'Заголовок обращения';
comment on column appeal.creator_id
    is 'Создатель обращения';

comment on column appeal.appeals_status
    is 'Статус обращения';
comment on column appeal.appeals_receipt_method
    is 'Способ получения обращения';
comment on column appeal.appeal_authors
    is 'Автор, соавторы обращения';
comment on column appeal.appeal_filepool
    is 'Несколько файлов';
comment on column appeal.appeal_question
    is 'Несколько вопросов';
comment on column appeal.nomenclature_id
    is 'Номенклатура';
comment on column appeal.resolution_id
    is 'Разрешение';
--comment on column appeal.thema_id
--    is 'Тема обращения';