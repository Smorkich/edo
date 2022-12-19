create table if not exists resolution
(
    id               bigserial                not null primary key,
    creation_date    timestamp with time zone not null,
    archived_date    timestamp with time zone,
    last_action_date timestamp with time zone not null,
    type             varchar(20)              not null,
    creator_id       bigint                   not null,
    signer_id        bigint                   not null,
    executor_id      bigint                   not null,
    curator_id       bigint                   not null,
    serial_number    varchar(50)              not null
);

comment on table resolution is 'Резолюция';
comment on column resolution.id is 'Id';
comment on column resolution.creation_date is 'Дата создания резолюции';
comment on column resolution.archived_date is 'Дата архивации резолюции';
comment on column resolution.last_action_date is 'Дата последнего действия над резолюцией';
comment on column resolution.type is 'Вид - резолюция, направление или запрос';
comment on column resolution.creator_id is 'Создатель резолюции';
comment on column resolution.signer_id is 'Принимающий резолюцию';
comment on column resolution.executor_id is 'Исполнители резолюции';
comment on column resolution.curator_id is 'Куратор исполнения';
comment on column resolution.serial_number is 'Номер резолюции';

