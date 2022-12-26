create table if not exists appeal_authors
(
    appeal_id bigint references edo.appeal (id),
    author_id bigint references edo.author (id)
);