create table if not exists appeal_addressee
(
    appeal_id bigint primary key references edo.appeal,
    employee_id bigint references edo.employee
);