-- Вставка роли "administration"
INSERT INTO roles (id, name)
VALUES ('00000000-0000-0000-0000-000000000001', 'administration');

-- Вставка роли "management"
INSERT INTO roles (id, name)
VALUES ('00000000-0000-0000-0000-000000000002', 'management');

-- Вставка разрешения "adminAccess"
INSERT INTO permissions (id, name, description)
VALUES ('bcb45d21-fff5-4386-b200-c17402a5534e', 'adminAccess', 'Доступ к административным функциям');

-- Вставка разрешения "managementAccess"
INSERT INTO permissions (id, name, description)
VALUES ('b614bfb7-e5d1-4e6c-934e-82cce89e4fc9', 'managementAccess', 'Доступ к функциям менеджера по продажам');

-- Связывание роли "administration" с разрешением "adminAccess"
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('00000000-0000-0000-0000-000000000001', 'bcb45d21-fff5-4386-b200-c17402a5534e');

-- Связывание роли "administration" с разрешением "managementAccess"
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('00000000-0000-0000-0000-000000000001', 'b614bfb7-e5d1-4e6c-934e-82cce89e4fc9');

-- Связывание роли "management" с разрешением "managementAccess"
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('00000000-0000-0000-0000-000000000002', 'b614bfb7-e5d1-4e6c-934e-82cce89e4fc9');

-- Вставка пользователя admin с уже рассчитанным хэшем
INSERT INTO users (id, email, password_hash, role_id)
VALUES (
           '6f5d7b5e-3c89-4a9f-9c9e-1b6d8c2f4a7d',
           'admin@example.com',
           '667b2b3848f442e47ed50c6c0adea890b381f4b0127f6afd8bcff43f1a9f2c9e',
           '00000000-0000-0000-0000-000000000001'
       );
