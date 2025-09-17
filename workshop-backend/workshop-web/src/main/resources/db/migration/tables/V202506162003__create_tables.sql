-- Table for AccessLevelEntity
CREATE TABLE access_levels (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Seed initial data
INSERT INTO access_levels (id, name, description, deleted, created_at, updated_at) VALUES
(UUID_TO_BIN(UUID(), 1), 'ADMIN', 'Nivel de Acesso de Administrador', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'USER', 'Nivel de Acesso de Usuario Padrao', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Table for UserRoleEntity
CREATE TABLE user_roles (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Seed initial data
INSERT INTO user_roles (id, name, description, deleted, created_at, updated_at) VALUES
(UUID_TO_BIN(UUID(), 1), 'STUDENT', 'Papel de Usuario: Estudante', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'EMPLOYEE', 'Papel de Usuario: Funcionario', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'TEACHER', 'Papel de Usuario: Professor', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
(UUID_TO_BIN(UUID(), 1), 'VISITOR', 'Papel de Usuario: Visitante Externo', FALSE, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));


-- Table for UserEntity
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) DEFAULT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_role_id BINARY(16) NOT NULL,
    access_level_id BINARY(16) NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_access DATETIME(6) DEFAULT NULL,
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT fk_users_user_role FOREIGN KEY (user_role_id) REFERENCES user_roles(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_users_access_level FOREIGN KEY (access_level_id) REFERENCES access_levels(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);


-- Table for RoomEntity
CREATE TABLE rooms (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    location VARCHAR(255) NOT NULL,
    deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);