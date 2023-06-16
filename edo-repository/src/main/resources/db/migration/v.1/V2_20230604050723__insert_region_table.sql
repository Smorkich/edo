INSERT INTO region (id, external_id, name_subject_rf, archiving_date, quantity,
                    federal_district_id, primary_branches,local_branches)
VALUES (1, '1', 'test_name_subject_rf', '2023-06-04', 'test_quantity',
        1, 'test_primary_branches', 'test_local_branches')
ON CONFLICT (id) DO NOTHING;