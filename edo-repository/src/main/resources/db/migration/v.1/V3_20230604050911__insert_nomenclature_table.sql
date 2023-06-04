INSERT INTO nomenclature (id, creation_date, archived_date, template, current_value, index)
VALUES (1, '2023-06-04', '2023-06-05', 'test_template', 1, 'test_index')
ON CONFLICT (id) DO NOTHING;