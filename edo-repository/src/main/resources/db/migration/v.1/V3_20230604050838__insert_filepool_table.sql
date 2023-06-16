INSERT INTO file_pool (id, storage_file_id, name, extension, size, page_count,
                       upload_date, archived_date, creator_id)
VALUES (1, '842eb0c9-8081-4d67-a582-9afafe06dd92', 'test.docx', 'docx', 11439, 11439,
        '2023-06-04 05:07:00', '2023-06-04 12:00:00', 1)
ON CONFLICT (id) DO NOTHING;