-- ==== FLYWAY MIGRATION: Create users table ====

CREATE TABLE users
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL,
    role         VARCHAR(50)  NOT NULL DEFAULT 'CUSTOMER',
    is_active    TINYINT(1)            DEFAULT 1,
    created_at   DATE                  DEFAULT (CURRENT_DATE),

    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


-- ==== FLYWAY MIGRATION: Add performance indexes to users table ====

-- Index for faster email lookups (though unique constraint already creates one)
CREATE INDEX idx_users_email ON users (email);

-- Index for role-based queries
CREATE INDEX idx_users_role ON users (role);

-- Index for active/inactive user filtering
CREATE INDEX idx_users_active ON users (is_active);

-- Index for recent user queries
CREATE INDEX idx_users_created_at ON users (created_at);