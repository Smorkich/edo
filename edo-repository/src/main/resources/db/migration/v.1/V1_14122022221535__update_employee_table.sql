alter table if exists employee
    add first_name varchar(60),
    add last_name varchar(30),
    add middle_mame varchar(30),
    add address varchar(30),
    add fio_dative varchar(30),
    add fio_nominative varchar(30),
    add fio_genitive varchar(30),
    add externalId bigint not null,
    add phone varchar(30),
    add workPhone varchar(30),
    add birthDate date,
    add username varchar(30),
    add creationDate timestamptz,
    add archivedDate timestamptz;

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

