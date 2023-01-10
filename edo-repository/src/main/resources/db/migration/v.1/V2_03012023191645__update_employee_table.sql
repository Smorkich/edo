ALTER TABLE employee
    DROP COLUMN first_name,
    DROP COLUMN last_name,
    DROP COLUMN middle_name,
    DROP COLUMN fio_dative,
    DROP COLUMN fio_nominative,
    DROP COLUMN fio_genitive,
    DROP COLUMN phone,
    DROP COLUMN work_phone,
    DROP COLUMN username,
    DROP COLUMN address;

alter table if exists employee
    add first_name varchar(100),
    add last_name varchar(100),
    add middle_name varchar(100),
    add fio_dative varchar(100),
    add fio_nominative varchar(100),
    add fio_genitive varchar(100),
    add phone varchar(100),
    add work_phone varchar(100),
    add username varchar(100),
    add department BIGINT REFERENCES department(id),
    add address BIGINT REFERENCES address(id);


comment on column employee.first_name
    is 'Имя';

comment on column employee.last_name
    is 'Фамилия';

comment on column employee.middle_name
    is 'Отчество';

comment on column employee.fio_dative
    is 'ФИО в дательном падеже';

comment on column employee.fio_nominative
    is 'ФИО в именительном падеже';

comment on column employee.fio_genitive
    is 'ФИО в родительном падеже';

comment on column employee.phone
    is 'Номер телефона сотовый';

comment on column employee.work_phone
    is 'Рабочий номер телефона';

comment on column employee.username
    is 'Имя пользователя';

comment on column employee.department
    is 'Департамент';

comment on column employee.address
    is 'Адрес';