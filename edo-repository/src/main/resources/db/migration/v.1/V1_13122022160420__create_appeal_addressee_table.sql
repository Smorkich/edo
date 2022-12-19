create table if not exists appeal_addressee
(
    appeal_id bigint primary key not null unique,
    employee_id bigint not null unique
);