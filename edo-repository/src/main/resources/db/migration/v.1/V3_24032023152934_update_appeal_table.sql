alter table if exists appeal
    add region_id bigint references region (id);

comment on column appeal.region_id is 'Привязка обращения к определенному региону';