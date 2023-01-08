ALTER TABLE employee DROP COLUMN address;

alter table if exists employee
    add department BIGINT REFERENCES department(id),
    add address BIGINT REFERENCES address(id);

comment on column employee.department
    is 'Департамент';

comment on column employee.address
    is 'Адрес';