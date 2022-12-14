create table if not exists Nomenclature (
    id serial primary key not null,
    creation_date DATE,
    archived_date DATE,
    template varchar(300),
    current_value int,
    "index" int
);

comment on table Nomenclature is 'Таблица номенкталура';
comment on column Nomenclature.id is 'идентификатор';
comment on column Nomenclature.creation_date is 'дата создания';
comment on column Nomenclature.archived_date is 'дата архивации';
comment on column Nomenclature.template is 'шаблон записи';
comment on column Nomenclature.current_value is 'текущее значение';
comment on column Nomenclature."index" is 'индекс записи';
