-- Migration: add nullable cpf column if missing (idempotent)
-- This migration will add the 'cpf' column to 'users' as nullable so it
-- is non-destructive on existing databases. Later migrations can enforce
-- NOT NULL / UNIQUE after data is backfilled.

-- MySQL / MariaDB syntax
ALTER TABLE users
  ADD COLUMN IF NOT EXISTS cpf VARCHAR(20) DEFAULT NULL;

-- Note: IF NOT EXISTS for ADD COLUMN is supported in recent MySQL/MariaDB.
-- If your DB does not support it, Flyway will fail; adapt accordingly.
