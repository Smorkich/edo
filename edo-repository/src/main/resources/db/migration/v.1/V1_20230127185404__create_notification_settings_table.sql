create table if not exists notification_settings
(
    id bigserial not null primary key,
    activation_status varchar(100),
    user_id varchar(255)
);