create table if not exists appeal_question
(
    appeal_id bigint references edo.appeal (id),
    question_id bigint references edo.question (id)
);