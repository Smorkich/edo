create table if not exists appeal_signer
(
    appeal_id bigint primary key not null unique,
    employee_id bigint not null unique
);