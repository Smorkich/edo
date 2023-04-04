CREATE TABLE IF NOT EXISTS execution_report(
        id BIGSERIAL NOT NULL PRIMARY KEY,
    creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
    execution_comment VARCHAR(200) NOT NULL,
    status VARCHAR NOT NULL
);

COMMENT ON TABLE execution_report IS 'таблица отчетов по исполнению резолюций';
COMMENT ON COLUMN execution_report.id IS 'идентификтаор';
COMMENT ON COLUMN execution_report.creationDate IS 'Дата создания отчета';
COMMENT ON COLUMN execution_report.execution_comment IS 'Коментарий к отчету';
COMMENT ON COLUMN execution_report.status IS 'Статус выполнения';
