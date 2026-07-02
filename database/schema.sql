
-- Create Database


CREATE DATABASE pastebin;

-->>> Connect to the pastebin database before running the remaining queries.



-- USERS TABLE TO STORE CREDENTIALS 


CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PASTES TABLE FOR STORING CONTENT FROM USER

CREATE TABLE pastes (
    paste_id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    visibility VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',
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
-- CATEGORIES TABLE 


CREATE TABLE categories 
(
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- INSERT LANGUAGE OPTIONS FOR CONTENT PREFERENCES IN CATAGORY TABLE

INSERT INTO categories (name) VALUES
('Java'),
('SQL'),
('Python'),
('JavaScript'),
('HTML'),
('CSS'),
('C'),
('C++'),
('Spring Boot'),
('JDBC'),
('DSA'),
('Others');