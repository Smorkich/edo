CREATE TABLE IF NOT EXISTS execution_report(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    execution_comment VARCHAR(200) NOT NULL,
    status VARCHAR NOT NULL,
    executor_id BIGINT NOT NULL ,
    resolution_id BIGINT NOT NULL

);

COMMENT ON TABLE execution_report IS 'таблица отчетов по исполнению резолюций';
COMMENT ON COLUMN execution_report.id IS 'идентификтаор';
COMMENT ON COLUMN execution_report.creation_date IS 'Дата создания отчета';
COMMENT ON COLUMN execution_report.execution_comment IS 'Коментарий к отчету';
COMMENT ON COLUMN execution_report.status IS 'Статус выполнения';
COMMENT ON COLUMN execution_report.executor_id IS 'идентификатор исполнителя';
COMMENT ON COLUMN execution_report.resolution_id IS 'идентификатор резолюции';
