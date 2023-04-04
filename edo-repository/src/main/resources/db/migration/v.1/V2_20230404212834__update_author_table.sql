alter table if exists author
    add response_type varchar(20);

comment on column author.response_type is 'Способ получения ответа автором';

