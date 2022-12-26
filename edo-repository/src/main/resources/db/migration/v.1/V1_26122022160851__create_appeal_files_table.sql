create table if not exists appeal_filepool
(
    appeal_id bigint references edo.appeal (id),
    filepool_id bigint references edo.file_pool (id)
);