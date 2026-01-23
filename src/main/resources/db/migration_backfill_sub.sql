-- Backfill auth0_sub for seed data to prevent NPEs in chat validation
UPDATE skillspot.benutzer
SET auth0_sub = 'seed:' || benutzer_id
WHERE auth0_sub IS NULL;

-- Enforce NOT NULL for future data integrity
ALTER TABLE skillspot.benutzer
ALTER COLUMN auth0_sub SET NOT NULL;
