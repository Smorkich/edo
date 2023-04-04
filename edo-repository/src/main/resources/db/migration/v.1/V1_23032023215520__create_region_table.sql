create table if not exists region
(
    id                  bigserial primary key,
    external_id         varchar(30),
    name_subject_rf     varchar(255),
    archiving_date      timestamp with time zone,
    quantity            varchar(30),
    federal_district_id bigint references federal_district (id),
    primary_branches    varchar(30),
    local_branches      varchar(30)
);



comment on table region is 'таблица субъектов Российской Федерации';
comment on column region.id is 'id';
comment on column region.external_id is 'Идентификатор региона из внешних систем';
comment on column region.name_subject_rf is 'Название субъекта Российской Федерации';
comment on column region.archiving_date is 'Дата архивации';
comment on column region.quantity is 'количество сотрудников';
comment on column region.federal_district_id is 'Связь с сайтом Федерального округа';
comment on column region.primary_branches is 'Количество первичных отделений в регионе';
comment on column region.local_branches is 'Количество местных отделений в регионе';