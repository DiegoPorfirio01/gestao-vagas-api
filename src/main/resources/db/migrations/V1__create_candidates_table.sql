CREATE TABLE candidates (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    description TEXT,
    curriculum TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
); 

ALTER TABLE candidates ADD CONSTRAINT uk_candidates_email UNIQUE (email);
ALTER TABLE candidates ADD CONSTRAINT uk_candidates_username UNIQUE (username);