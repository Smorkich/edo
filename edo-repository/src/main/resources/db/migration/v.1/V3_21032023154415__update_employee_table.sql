ALTER TABLE if EXISTS employee
    ADD COLUMN email varchar(60);

comment on column employee.email
    is 'Почта';