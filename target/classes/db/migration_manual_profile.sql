-- Skillspot Profil Erweiterung
-- Sicherstellen, dass das Schema existiert
CREATE SCHEMA IF NOT EXISTS skillspot;

-- Tabelle skillspot.benutzer erweitern
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS auth0_sub VARCHAR(255);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS rolle VARCHAR(50);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS display_name VARCHAR(255);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS address TEXT;
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS location_lat NUMERIC(10,6);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS location_lon NUMERIC(10,6);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT now();
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT now();

-- Falls Seed-Daten existieren, setzen wir temporaer auth0_sub
UPDATE skillspot.benutzer SET auth0_sub = 'local-seed-' || benutzer_id WHERE auth0_sub IS NULL;

-- Unique Constraint fuer auth0_sub
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'benutzer_auth0_sub_key') THEN
        ALTER TABLE skillspot.benutzer ADD CONSTRAINT benutzer_auth0_sub_key UNIQUE (auth0_sub);
    END IF;
END $$;

-- CHECK Constraint fuer rolle
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'check_rolle') THEN
        ALTER TABLE skillspot.benutzer ADD CONSTRAINT check_rolle CHECK (rolle IN ('USER', 'PROVIDER'));
    END IF;
END $$;
