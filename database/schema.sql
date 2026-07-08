-- ============================================================
-- PasteBin Database Schema
-- PostgreSQL
-- ============================================================

-- ============================================================
-- USERS
-- ============================================================

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,

    username VARCHAR(50) NOT NULL UNIQUE,

    email VARCHAR(100) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- CATEGORIES
-- ============================================================

CREATE TABLE categories (

    category_id SERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- PASTES
-- ============================================================

CREATE TABLE pastes (

    paste_id SERIAL PRIMARY KEY,

    public_id VARCHAR(100) NOT NULL UNIQUE,

    title VARCHAR(200) NOT NULL,

    content TEXT NOT NULL,

    visibility VARCHAR(20)
        NOT NULL
        DEFAULT 'PUBLIC'
        CHECK (visibility IN ('PUBLIC','PRIVATE','UNLISTED')),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    user_id INT NOT NULL,

    category_id INT NOT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id)
);

-- ============================================================
-- DEFAULT CATEGORIES
-- ============================================================

INSERT INTO categories (name)
VALUES
('Java'),
('SQL'),
('Python'),
('JavaScript'),
('TypeScript'),
('HTML'),
('CSS'),
('C'),
('C++'),
('Spring'),
('Spring Boot'),
('Hibernate'),
('JDBC'),
('PostgreSQL'),
('DSA'),
('Algorithms'),
('System Design'),
('DevOps'),
('Docker'),
('Kubernetes'),
('Git'),
('Linux'),
('Others');