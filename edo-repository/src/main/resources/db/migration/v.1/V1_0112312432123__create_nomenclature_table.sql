create table if not exists nomenclature (
    id serial primary key not null,
    creation_date timestamp with time zone NOT NULL
   DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    archived_date timestamp with time zone NOT NULL
   DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    template varchar(300),
    current_value int,
    "index" int
);

comment on table nomenclature is 'Таблица номенкталура';
comment on column nomenclature.id is 'идентификатор';
comment on column nomenclature.creation_date is 'дата создания';
comment on column nomenclature.archived_date is 'дата архивации';
comment on column nomenclature.template is 'шаблон записи';
comment on column nomenclature.current_value is 'текущее значение';
comment on column nomenclature."index" is 'индекс записи';
