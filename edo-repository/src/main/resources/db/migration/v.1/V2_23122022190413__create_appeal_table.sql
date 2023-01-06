alter table if exists appeal
    drop column creator_id,
    add creator_id             bigint references employee (id),
    add appeals_status         varchar(255),
    add appeals_receipt_method varchar(255),
    add nomenclature_id        bigint references nomenclature (id);

comment on column appeal.creator_id
    is 'Создатель обращения';
comment on column appeal.appeals_status
    is 'Статус обращения';
comment on column appeal.appeals_receipt_method
    is 'Способ получения обращения';
comment on column appeal.nomenclature_id
    is 'Номенклатура';