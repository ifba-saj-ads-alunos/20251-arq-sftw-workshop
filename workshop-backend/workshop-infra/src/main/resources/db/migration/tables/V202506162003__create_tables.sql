-- V202506162003__create_tables.sql

-- Tabela para AccessLevelEntity
-- O campo 'name' armazena o nome da enum AccessLevel (ADMIN, USER)
CREATE TABLE access_levels (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(50) UNIQUE NOT NULL, -- Nome do enum como String, deve ser único
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    date_create DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    date_update DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Inserção de dados iniciais para access_levels
INSERT INTO access_levels (id, name, description, deleted, date_create, date_update) VALUES
(UUID_TO_BIN(UUID(), 1), 'ADMIN', 'Nível de Acesso de Administrador', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'USER', 'Nível de Acesso de Usuário Padrão', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Tabela para UserRoleEntity
-- O campo 'name' armazena o nome da enum UserRole (STUDENT, EMPLOYEE, TEACHER, VISITOR)
CREATE TABLE user_roles (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(50) UNIQUE NOT NULL, -- Nome do enum como String, deve ser único
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    date_create DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    date_update DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Inserção de dados iniciais para user_roles
INSERT INTO user_roles (id, name, description, deleted, date_create, date_update) VALUES
(UUID_TO_BIN(UUID(), 1), 'STUDENT', 'Papel de Usuário: Estudante', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'EMPLOYEE', 'Papel de Usuário: Funcionário', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'TEACHER', 'Papel de Usuário: Professor (especialização de Funcionário)', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'VISITOR', 'Papel de Usuário: Visitante Externo', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Tabela para UserEntity
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_role_id BINARY(16) NOT NULL, -- Chave estrangeira para user_roles
    access_level_id BINARY(16) NOT NULL, -- Chave estrangeira para access_levels
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    date_create DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    date_update DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    -- Definição das chaves estrangeiras
    CONSTRAINT fk_users_user_role FOREIGN KEY (user_role_id) REFERENCES user_roles(id),
    CONSTRAINT fk_users_access_level FOREIGN KEY (access_level_id) REFERENCES access_levels(id)
);