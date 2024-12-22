CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    website VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
); 

ALTER TABLE companies ADD CONSTRAINT uk_companies_slug UNIQUE (slug);
ALTER TABLE companies ADD CONSTRAINT uk_companies_email UNIQUE (email);