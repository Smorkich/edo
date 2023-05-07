--добавление employee
INSERT INTO edo.employee (id, first_name, last_name, middle_name, fio_dative, fio_nominative, fio_genitive, external_id,
                          phone, work_phone, birth_date, username, creation_date, archived_date, notification_id,
                          department, address)
VALUES (DEFAULT, null, null, null, null, 'signer', null, '123', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'signer', null, '12', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'creator', null, '13', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'addresse', null, '132', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'addresse', null, '14', null, null, null, null, null, null, null, null, null);

/*
--добавление question
INSERT INTO edo.question (id, creation_date, archived_date, summary, theme_id) 
VALUES
(DEFAULT, CURRENT_DATE, null, 'qwe', 1),
(DEFAULT, CURRENT_DATE, null, 'ewq', 2);
 */

--добавление nomenclature
INSERT INTO edo.nomenclature (id, creation_date, archived_date, template, current_value, index)
VALUES (DEFAULT, null, null, null, null, 121212);

/*
--добавление author
INSERT INTO edo.author (id, first_name, last_name, middle_name, address, snils, mobile_phone, email, employment, fio_dative, fio_genitive, fio_nominative, response_type)
VALUES
(DEFAULT,'Имя','Фамилия',null,null,null,'89877899889', 'author@mail.ru',null, null, null, 'ФИО', null),
(DEFAULT,'Имя','Фамилия',null,null,null,'89877899888', 'author@yandex.ru',null, null, null, 'ФИО',null);
 */

--добавление region
INSERT INTO edo.region (external_id, name_subject_rf, archiving_date, quantity, federal_district_id, primary_branches,
                        local_branches)
VALUES (DEFAULT, null, null, null, null, null, null);




