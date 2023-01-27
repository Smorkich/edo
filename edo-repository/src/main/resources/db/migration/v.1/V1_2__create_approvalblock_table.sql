create table if not exists approval_block
(
    id                                bigserial primary key,
    approvalblock_type                varchar(45) not null,
    approval_id                       bigint references approval (id),
    member_who_redirected_approval_id bigint references member (id),
    ordinal_number                    int
);

comment on table approval_block is 'Блок c участниками или подписантами согласования';
comment on column approval_block.id is 'Id';
comment on column approval_block.approvalblock_type is 'Тип блока листа согласования';
comment on column approval_block.approval_id is 'Лист согласования, за которым закреплён блок';
comment on column approval_block.member_who_redirected_approval_id is 'Участник перенаправивший лист согласования';
comment on column approval_block.ordinal_number is 'Номер по порядку согласования и порядку отображения на UI';