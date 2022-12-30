alter table if exists author
    add address  varchar(255),
    add email        varchar(255),
    add mobile_phone varchar(60);

comment on column author.address
    is 'Адрес';
comment on column author.email
    is 'Адрес электронной почты';
comment on column author.mobile_phone
    is 'Номер телефона';