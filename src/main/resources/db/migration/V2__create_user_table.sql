CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS users CASCADE;
DROP TYPE IF EXISTS user_role;

CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE users(
    id uuid primary key default gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    hashed_password TEXT NOT NULL,
    is_active BOOLEAN DEFAULT false,
    role VARCHAR(50) DEFAULT 'USER'
);

CREATE INDEX idx_user_id_active ON users (id, is_active);