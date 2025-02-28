-- Вставка роли "subscriber"
INSERT INTO roles (id, name)
VALUES ('ab23c072-e407-4f31-8e6e-d2452ef9bbf3', 'subscriber');

-- Вставка разрешения "subscriberAccess"
INSERT INTO permissions (id, name, description)
VALUES ('7e2e572c-db03-4a2b-9afb-8f31d009591b', 'subscriberAccess', 'Доступ к функциям подписчика');

-- Связывание роли "subscriber" с разрешением "subscriberAccess"
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('ab23c072-e407-4f31-8e6e-d2452ef9bbf3', '7e2e572c-db03-4a2b-9afb-8f31d009591b');
