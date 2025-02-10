-- Установка search_path
ALTER DATABASE subscription_system SET search_path TO subscription_system;

-- Таблица ролей пользователей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       permissions JSONB
);

-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       role_id INT REFERENCES roles(id) ON DELETE SET NULL
);

-- Таблица типов подписок
CREATE TABLE subscription_types (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL UNIQUE,
                                    duration_days INT NOT NULL,
                                    price DECIMAL(10, 2),
                                    features JSONB
);

-- Таблица подписок (удалено GENERATED поле, добавлен статус с триггером)
CREATE TABLE subscriptions (
                               id SERIAL PRIMARY KEY,
                               user_id INT REFERENCES users(id) ON DELETE CASCADE,
                               type_id INT REFERENCES subscription_types(id) ON DELETE SET NULL,
                               start_date DATE NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               end_date DATE,
                               status VARCHAR(50)  -- Статус обновляется через триггер
);

-- Функция для установки даты окончания подписки
CREATE OR REPLACE FUNCTION set_end_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.end_date IS NULL THEN
        SELECT NEW.start_date + (duration_days * INTERVAL '1 day')
        INTO NEW.end_date
        FROM subscription_types
        WHERE id = NEW.type_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для автоматического вычисления end_date
CREATE TRIGGER trigger_set_end_date
    BEFORE INSERT OR UPDATE ON subscriptions
    FOR EACH ROW
EXECUTE FUNCTION set_end_date();

-- Функция для обновления статуса подписки
CREATE OR REPLACE FUNCTION update_subscription_status()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.status := CASE
                      WHEN CURRENT_DATE <= NEW.end_date THEN 'Active'
                      ELSE 'Expired'
        END;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для обновления статуса
CREATE TRIGGER trigger_update_status
    BEFORE INSERT OR UPDATE ON subscriptions
    FOR EACH ROW
EXECUTE FUNCTION update_subscription_status();

-- Таблица сессий пользователей
CREATE TABLE sessions (
                          id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES users(id) ON DELETE CASCADE,
                          device_id VARCHAR(100) NOT NULL,
                          token TEXT NOT NULL,
                          last_active TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
