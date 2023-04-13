CREATE TABLE IF NOT EXISTS deadline_resolution
(
    id                bigserial primary key,
    deadline          date,
    transfer_deadline text,
    resolution_id     bigint references resolution (id)
);

comment on table deadline_resolution is 'установка крайнего срока для резолюции';
comment on column deadline_resolution.id is 'id';
comment on column deadline_resolution.deadline is 'Крайний срок';
comment on column deadline_resolution.transfer_deadline is 'причина переноса';
comment on column deadline_resolution.resolution_id is 'порядковый номер резолюции';

