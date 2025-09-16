-- Tabela para AccessLevelEntity
CREATE TABLE access_levels (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(50) UNIQUE NOT NULL, -- Nome do enum como String, deve ser único
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Inserção de dados iniciais
INSERT INTO access_levels (id, name, description, deleted, created_at, updated_at) VALUES
(UUID_TO_BIN(UUID(), 1), 'ADMIN', 'Nível de Acesso de Administrador', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'USER', 'Nível de Acesso de Usuário Padrão', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Tabela para UserRoleEntity
CREATE TABLE user_roles (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(50) UNIQUE NOT NULL, -- Nome do enum como String, deve ser único
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Inserção de dados iniciais
INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at) VALUES
(UUID_TO_BIN(UUID(), 1), 'STUDENT', 'Papel de Usuário: Estudante', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'EMPLOYEE', 'Papel de Usuário: Funcionário', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'TEACHER', 'Papel de Usuário: Professor (especialização de Funcionário)', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'VISITOR', 'Papel de Usuário: Visitante Externo', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Tabela para UserEntity
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY, -- UUID armazenado como BINARY(16)
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_role_id BINARY(16) NOT NULL, -- Chave estrangeira para user_roles
    access_level_id BINARY(16) NOT NULL, -- Chave estrangeira para access_levels
    password VARCHAR(255) NOT NULL,
    last_access DATETIME(6) DEFAULT NULL,
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    -- Foreign keys
    CONSTRAINT fk_users_user_role FOREIGN KEY (user_role_id) REFERENCES user_roles(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_users_access_level FOREIGN KEY (access_level_id) REFERENCES access_levels(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);