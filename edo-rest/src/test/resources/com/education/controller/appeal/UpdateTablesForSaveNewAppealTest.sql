--добавление employee
INSERT INTO edo.employee (id, first_name, last_name, middle_name, fio_dative, fio_nominative, fio_genitive, external_id,
                          phone, work_phone, birth_date, username, creation_date, archived_date, notification_id,
                          department, address)
VALUES (DEFAULT, null, null, null, null, 'signer', null, '123', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'signer', null, '12', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'creator', null, '13', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'addresse', null, '132', null, null, null, null, null, null, null, null, null),
       (DEFAULT, null, null, null, null, 'addresse', null, '14', null, null, null, null, null, null, null, null, null);


--добавление nomenclature
INSERT INTO edo.nomenclature (id, creation_date, archived_date, template, current_value, index)
VALUES (DEFAULT, null, null, null, null, 121212);


--добавление region
INSERT INTO edo.region (external_id, name_subject_rf, archiving_date, quantity, federal_district_id, primary_branches,
                        local_branches)
VALUES (DEFAULT, null, null, null, null, null, null);

--добавление FilePool
INSERT INTO edo.file_pool (id, storage_file_id, name, extension, size, page_count, upload_date, archived_date, creator_id)
VALUES (DEFAULT, gen_random_uuid(), 'File', 'Y', 3, 1, CURRENT_DATE, null, 3);



