-- Estructura inicial para AnchorMind Pro (Fase 3)

-- Tabla de Usuarios
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    is_subscribed_to_newsletter BOOLEAN DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla de Registros de Ansiedad (Vinculada a User)
CREATE TABLE anxiety_records (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    raw_input TEXT,
    anxiety_level INTEGER,
    trigger VARCHAR(255),
    -- Datos de la respuesta de IA
    technique TEXT,
    awareness_message TEXT,
    action_steps TEXT[],

    CONSTRAINT fk_user_records FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Índice para mejorar búsquedas por username (usado en JPQL)
CREATE INDEX idx_users_username ON users(username);