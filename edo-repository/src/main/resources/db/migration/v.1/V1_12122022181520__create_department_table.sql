CREATE TABLE IF NOT EXISTS department(
    id BIGINT NOT NULL PRIMARY KEY,
    short_name VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    external_id BIGINT NOT NULL ,
    phone INT NOT NULL,
    creation_date DATE NOT NULL,
    archived_date DATE NOT NULL

);
COMMENT  ON COLUMN department.id is 'айдишник';
COMMENT  ON COLUMN department.short_name is 'короткое имя';
COMMENT  ON COLUMN department.full_name is 'полное имя';
COMMENT  ON COLUMN department.address is 'адресс';
COMMENT  ON COLUMN department.external_id is 'внешний индификатор, подгружаем из чужого хранилища';
COMMENT  ON COLUMN department.phone is 'номер телефона';
COMMENT  ON COLUMN department.creation_date is 'дата создания';
COMMENT  ON COLUMN department.archived_date is 'дата архивации';