ALTER DATABASE ifba_workshop
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Timezone global (UTC)
SET GLOBAL time_zone = '+00:00';

-- Engine padrão para novas tabelas
SET default_storage_engine = InnoDB;

-- SQL Mode seguro
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';