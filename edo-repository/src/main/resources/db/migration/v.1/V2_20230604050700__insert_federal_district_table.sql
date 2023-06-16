INSERT INTO federal_district (id, name, website)
VALUES (1, 'test_federal_district_name', 'test-federal-website.com')
ON CONFLICT (id) DO NOTHING;