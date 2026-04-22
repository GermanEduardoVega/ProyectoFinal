-- V3__Add_missing_user_details.sql

-- 1. Agregamos solo lo que falta en la tabla de USUARIOS
ALTER TABLE users
ADD COLUMN full_name VARCHAR(255),
ADD COLUMN email VARCHAR(255),
ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP;

-- 2. Aseguramos que el email sea único
ALTER TABLE users
ADD CONSTRAINT uk_user_email UNIQUE (email);