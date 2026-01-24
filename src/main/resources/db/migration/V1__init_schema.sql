-- SKILLSPOT UNIFIED DATABASE SCHEMA INITIALIZATION
-- Consolidates manual_profile, backfill, and chat migrations v2-v4

CREATE SCHEMA IF NOT EXISTS skillspot;

---------------------------------------------------------
-- 1. USER PROFILE EXTENSIONS (benutzer)
---------------------------------------------------------
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS auth0_sub VARCHAR(255);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS rolle VARCHAR(50);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS display_name VARCHAR(255);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS address TEXT;
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS location_lat NUMERIC(10,6);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS location_lon NUMERIC(10,6);
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT now();
ALTER TABLE skillspot.benutzer ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT now();

-- Backfill auth0_sub for seed data to prevent NPEs and enable constraints
UPDATE skillspot.benutzer 
SET auth0_sub = 'seed:' || benutzer_id 
WHERE auth0_sub IS NULL;

-- Enforce constraints on benutzer
ALTER TABLE skillspot.benutzer ALTER COLUMN auth0_sub SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'benutzer_auth0_sub_key') THEN
        ALTER TABLE skillspot.benutzer ADD CONSTRAINT benutzer_auth0_sub_key UNIQUE (auth0_sub);
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'check_rolle') THEN
        ALTER TABLE skillspot.benutzer ADD CONSTRAINT check_rolle CHECK (rolle IN ('USER', 'PROVIDER'));
    END IF;
END $$;


---------------------------------------------------------
-- 2. CHAT SYSTEM
---------------------------------------------------------

-- Chat Thread Table
CREATE TABLE IF NOT EXISTS skillspot.chat_thread (
    thread_id BIGSERIAL PRIMARY KEY,
    dienstleistung_id BIGINT NOT NULL,
    user_sub VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE(user_sub, dienstleistung_id)
);

-- Chat Message Table (includes is_read from v4)
CREATE TABLE IF NOT EXISTS skillspot.chat_message (
    message_id BIGSERIAL PRIMARY KEY,
    thread_id BIGINT NOT NULL REFERENCES skillspot.chat_thread(thread_id) ON DELETE CASCADE,
    sender_sub VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Indexes for Chat Performance
CREATE INDEX IF NOT EXISTS idx_chat_message_thread_is_read ON skillspot.chat_message(thread_id, is_read);
CREATE INDEX IF NOT EXISTS idx_chat_message_sender_sub ON skillspot.chat_message(sender_sub);
