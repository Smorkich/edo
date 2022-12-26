alter table if exists appeal
    add appeals_status         varchar(255),
    add appeals_receipt_method varchar(255),
    add appeal_authors         varchar(255),
    add appeal_filepool        varchar(255),
    add appeal_question        varchar(255),
    add nomenclature_id        bigint references edo.nomenclature (id);

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