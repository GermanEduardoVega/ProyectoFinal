-- V2__Create_Clinic_Table_And_Update_Anxiety_Records.sql

-- 1. Crear la tabla de Clínicas
CREATE TABLE clinics (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Modificar la tabla de Usuarios para vincularlos a una Clínica
ALTER TABLE users ADD COLUMN clinic_id BIGINT;

-- 3. Crear la llave foránea (Constraint) en Users
ALTER TABLE users
ADD CONSTRAINT fk_user_clinic
FOREIGN KEY (clinic_id) REFERENCES clinics(id);

-- 4. Corregir y Agregar columnas faltantes en anxiety_records para que coincidan con Hibernate
-- Renombramos las existentes para evitar errores de mapeo
ALTER TABLE anxiety_records RENAME COLUMN timestamp TO time_stamp;
ALTER TABLE anxiety_records RENAME COLUMN trigger TO trigger_identified;

-- Agregamos las que Hibernate intentó insertar y no encontró
ALTER TABLE anxiety_records ADD COLUMN ai_response_json TEXT;
ALTER TABLE anxiety_records ADD COLUMN aplicability TEXT; -- Mantengo una sola 'p' según tu log de error