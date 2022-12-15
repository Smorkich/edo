DROP TABLE IF EXISTS employee CASCADE;

create table if not exists employee
(
    id bigint not null primary key,
    first_name varchar(60),
    last_name varchar(30),
    middle_mame varchar(30),
    address varchar(30),
    fio_dative varchar(30),
    fio_nominative varchar(30),
    fio_genitive varchar(30),
    externalId bigserial not null,
    phone varchar(30),
    workPhone varchar(30),
    birthDate date,
    username varchar(30),
    creationDate timestamptz,
    archivedDate timestamptz
);

comment on table employee
    is 'Сотрудник';

comment on column employee.first_name
    is 'Имя';

comment on column employee.last_name
    is 'Фамилия';

comment on column employee.middle_mame
    is 'Отчество';

comment on column employee.address
    is 'Адрес';

comment on column employee.fio_dative
    is 'ФИО в дательном падеже';

comment on column employee.fio_nominative
    is 'ФИО в именительном падеже';

comment on column employee.fio_genitive
    is 'ФИО в родительном падеже';

comment on column employee.externalId
    is 'Внешний индификатор, который будем получать из чужого хранилища';

comment on column employee.phone
    is 'Номер телефона сотовый';

comment on column employee.workPhone
    is 'Рабочий номер телефона';

comment on column employee.birthDate
    is 'Дата рождения';

comment on column employee.username
    is 'Имя пользователя';

comment on column employee.username
    is 'Дата создания';

comment on column employee.username
    is 'Дата архивации';

