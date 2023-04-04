create table if not exists federal_district
(
    id      bigserial primary key,
    name    varchar(255),
    website varchar(255)

);

comment on column federal_district.id is 'id';
comment on column federal_district.name is 'Имя региона';
comment on column federal_district.website is 'сайт региона';
