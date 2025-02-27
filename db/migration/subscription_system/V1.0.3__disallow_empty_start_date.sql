CREATE OR REPLACE FUNCTION set_default_start_date()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.start_date IS NULL THEN
        NEW.start_date := CURRENT_DATE;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_default_start_date
    BEFORE INSERT ON subscriptions
    FOR EACH ROW
    EXECUTE FUNCTION set_default_start_date();
