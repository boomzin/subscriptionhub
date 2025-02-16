-- Вставка роли "administration"
INSERT INTO roles (id, name)
VALUES ('00000000-0000-0000-0000-000000000001', 'administration');

-- Вставка разрешения "adminAccess"
INSERT INTO permissions (id, name, description)
VALUES ('00000000-0000-0000-0000-000000000002', 'adminAccess', 'Доступ к административным функциям');

-- Связывание роли "administration" с разрешением "adminAccess"
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002');

-- Вставка пользователя admin с уже рассчитанным хэшем
INSERT INTO users (id, email, password_hash, role_id)
VALUES (
           '6f5d7b5e-3c89-4a9f-9c9e-1b6d8c2f4a7d',
           'admin@example.com',
           '2d7e3c583c66d4739ea693dff69a7c82dc4e94e5b0546977d5acaf84c1fa79d7',
           '00000000-0000-0000-0000-000000000001'
       );
