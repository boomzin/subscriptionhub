CREATE TYPE subscription_status AS ENUM ('ACTIVE', 'EXPIRED', 'CANCELLED', 'PENDING', 'SUSPENDED');

ALTER TABLE subscriptions
    ALTER COLUMN status SET DATA TYPE subscription_status
    USING status::subscription_status;