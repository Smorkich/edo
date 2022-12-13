CREATE TABLE file_pool
(
    storage_file_id BIGSERIAL NOT NULL PRIMARY KEY,
    name            VARCHAR   NOT NULL,
    extension       VARCHAR   NOT NULL,
    size            INTEGER   NOT NULL,
    page_count      INTEGER   NOT NULL,
    upload_date     DATE      NOT NULL,
    archived_date   DATE,
    creator         VARCHAR
);

COMMENT ON TABLE file_pool is 'хранилище обращений граждан';
COMMENT ON COLUMN file_pool.storage_file_id is 'id обращения';
COMMENT ON COLUMN file_pool.name is 'имя обращения';
COMMENT ON COLUMN file_pool.extension is 'продление обращения';
COMMENT ON COLUMN file_pool.size is 'размер';
COMMENT ON COLUMN file_pool.page_count is 'количество страниц';
COMMENT ON COLUMN file_pool.upload_date is 'дата создания';
COMMENT ON COLUMN file_pool.archived_date is 'дата архивации';
COMMENT ON COLUMN file_pool.creator is 'создатель обращения';