-- Migration: set cpf NOT NULL and UNIQUE (apply only after backfilling values)
-- WARNING: apply this migration only after you've populated cpf for all existing users.
-- This migration enforces data integrity by making cpf required and unique.

-- Add UNIQUE index (safe if values are unique)
ALTER TABLE users
  ADD CONSTRAINT uq_users_cpf UNIQUE (cpf);

-- Make column NOT NULL
ALTER TABLE users
  MODIFY COLUMN cpf VARCHAR(20) NOT NULL;

-- IMPORTANT: Do not enable this migration until you've verified
-- that all existing users have non-null, unique CPF values.
