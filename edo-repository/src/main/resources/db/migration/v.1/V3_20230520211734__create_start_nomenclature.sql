INSERT INTO nomenclature (id,template,current_value,index)
VALUES (1,'%ИНДЕКС-%ГОД/2-%НОМЕР',1,'СОГЛ') ON CONFLICT (id) DO NOTHING;