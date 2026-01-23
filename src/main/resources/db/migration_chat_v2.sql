-- Migration for Chat Thread Basis
CREATE TABLE IF NOT EXISTS skillspot.chat_thread (
    thread_id BIGSERIAL PRIMARY KEY,
    dienstleistung_id BIGINT NOT NULL,
    user_sub VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE(user_sub, dienstleistung_id)
);
