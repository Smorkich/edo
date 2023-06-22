ALTER TABLE IF EXISTS question
    ADD COLUMN IF NOT EXISTS question_status VARCHAR(100);

COMMENT ON COLUMN question.question_status
    IS 'Статус вопроса';
