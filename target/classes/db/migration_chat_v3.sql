-- Migration for Full Chat Functionality (Messages)
CREATE TABLE IF NOT EXISTS skillspot.chat_message (
    message_id BIGSERIAL PRIMARY KEY,
    thread_id BIGINT NOT NULL REFERENCES skillspot.chat_thread(thread_id) ON DELETE CASCADE,
    sender_sub VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);
