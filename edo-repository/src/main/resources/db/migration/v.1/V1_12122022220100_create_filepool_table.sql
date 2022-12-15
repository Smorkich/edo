CREATE TABLE file_pool
(
    storage_file_id VARCHAR     NOT NULL,
    name            VARCHAR     NOT NULL,
    extension       VARCHAR     NOT NULL,
    size            INTEGER     NOT NULL,
    page_count      INTEGER     NOT NULL,
    upload_date     timestamptz NOT NULL,
    archived_date   timestamptz,
    creator_id  BIGINT     NOT NULL,
    CONSTRAINT FK_file_pool_Employee FOREIGN KEY (creator_id) REFERENCES employee (ID)
);


COMMENT ON TABLE file_pool is 'информация о файле, загруженном в хранилище';
COMMENT ON COLUMN file_pool.storage_file_id is 'ключ для получения файла';
COMMENT ON COLUMN file_pool.name is 'имя файла';
COMMENT ON COLUMN file_pool.extension is 'продление обращения';
COMMENT ON COLUMN file_pool.size is 'размер';
COMMENT ON COLUMN file_pool.page_count is 'количество страниц';
COMMENT ON COLUMN file_pool.upload_date is 'дата загрузки';
COMMENT ON COLUMN file_pool.archived_date is 'дата архивации';
COMMENT ON COLUMN file_pool.creator_id is 'создатель файла';