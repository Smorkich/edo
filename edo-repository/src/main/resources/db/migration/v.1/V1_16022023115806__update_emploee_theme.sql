CREATE TABLE IF NOT EXISTS question_topics
(
    id 				BIGSERIAL NOT NULL PRIMARY KEY,
    theme_name 		VARCHAR(300),
    code 			VARCHAR(30)
);

COMMENT ON TABLE question_topics  IS 'Таблица тем';
COMMENT ON COLUMN question_topics.id IS 'Идентификатор';
COMMENT ON COLUMN question_topics.theme_name IS 'Название темы';
COMMENT ON COLUMN question_topics.code IS 'Код темы';