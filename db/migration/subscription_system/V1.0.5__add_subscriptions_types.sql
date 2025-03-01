INSERT INTO subscription_types (id, name, duration_days, price) VALUES
    (gen_random_uuid(), '1 день', 1, 0),
    (gen_random_uuid(), '1 неделя', 7, 1000),
    (gen_random_uuid(), '1 месяц', 30, 25000),
    (gen_random_uuid(), '1 квартал', 90, 70000),
    (gen_random_uuid(), '1 полугодие', 180, 130000),
    (gen_random_uuid(), '1 год', 365, 240000);