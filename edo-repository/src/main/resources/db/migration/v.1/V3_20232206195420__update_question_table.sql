ALTER TABLE IF EXISTS question
    ADD COLUMN IF NOT EXISTS question_status VARCHAR(100),
    ADD COLUMN IF NOT EXISTS appeal_id BIGINT REFERENCES appeal(id);

DROP TABLE IF EXISTS appeal_question;

COMMENT ON COLUMN question.question_status
    IS 'Статус вопроса';
