create table if not exists appeal_signer
(
    appeal_id bigint primary key references edo.appeal,
    employee_id bigint references edo.employee
);