INSERT INTO edo.address (id, full_address, street, house, "index", housing, building, city,
                         region, country, flat, longitude, latitude)
VALUES (1, 'test_address', 'test_street', '1', 127031, '3', '1', 'test_city',
        'test_region', 'test_country', 'test_flat', 'test_longitude', 'test_latitude')
ON CONFLICT (id) DO NOTHING;


INSERT INTO edo.department (id, short_name, full_name, address, external_id, phone, creation_date, archived_date)
VALUES (1, 'test_short_name', 'test_full_name', 1, '1', 'test_phone', '2023-05-02', '2023-05-02')
ON CONFLICT (id) DO NOTHING;


INSERT INTO edo.employee(id, first_name, last_name, middle_name, fio_dative, fio_nominative,
                         fio_genitive, external_id, phone, work_phone, birth_date, username,
                         creation_date, archived_date, department, address)
VALUES (1, 'test_first_name', 'test_last_name','test_middle_name', 'test_fio_dative', 'test_fio_nominative',
        'test_fio_genitive', '1', 'test_phone', 'test_work_phone', '1995-08-24', 'test_username',
        '2023-05-02','2023-05-02', 1, 1)
ON CONFLICT (id) DO NOTHING;
