CREATE TABLE IF NOT EXISTS facsimile
(
    id            BIGSERIAL PRIMARY KEY,
    is_archived   BOOLEAN  DEFAULT false,
    employee_id   BIGINT NOT NULL REFERENCES employee (id),
    department_id BIGINT NOT NULL REFERENCES department (id),
    file_pool_id  BIGINT NOT NULL REFERENCES file_pool (id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT fk_file_pool FOREIGN KEY (file_pool_id) REFERENCES file_pool (id)
);

COMMENT ON COLUMN facsimile.is_archived IS 'Признак архивности';
COMMENT ON COLUMN facsimile.employee_id IS 'Cвязь с пользователем';
COMMENT ON COLUMN facsimile.department_id IS 'Cвязь с департаментом';
COMMENT ON COLUMN facsimile.file_pool_id IS 'Cвязь с файлом';

ALTER TABLE facsimile
    ALTER COLUMN is_archived SET DEFAULT false;
