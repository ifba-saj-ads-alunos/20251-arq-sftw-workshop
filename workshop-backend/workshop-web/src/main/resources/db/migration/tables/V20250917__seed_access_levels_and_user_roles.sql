-- Migration: seed access_levels and user_roles (idempotent)
-- Inserts required enums/lookup rows used by the application.

-- MySQL compatible SQL, uses UUID_TO_BIN for binary UUID storage

-- Access levels
INSERT INTO access_levels (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'ADMIN', 'Nível de Acesso de Administrador', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM access_levels WHERE name = 'ADMIN' AND deleted = FALSE);

INSERT INTO access_levels (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'USER', 'Nível de Acesso de Usuário Padrão', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM access_levels WHERE name = 'USER' AND deleted = FALSE);

-- User roles
INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'STUDENT', 'Papel de Usuário: Estudante', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE name = 'STUDENT' AND deleted = FALSE);

INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'EMPLOYEE', 'Papel de Usuário: Funcionário', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE name = 'EMPLOYEE' AND deleted = FALSE);

INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'TEACHER', 'Papel de Usuário: Professor', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE name = 'TEACHER' AND deleted = FALSE);

INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at)
SELECT UUID_TO_BIN(UUID(), 1), 'VISITOR', 'Papel de Usuário: Visitante Externo', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE name = 'VISITOR' AND deleted = FALSE);

-- Notes:
-- - This is written for MySQL (UUID_TO_BIN). If you run Flyway on H2,
--   you might need to adapt or skip this migration in local profile.
