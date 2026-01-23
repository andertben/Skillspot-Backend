-- Add is_read column to chat_message
ALTER TABLE skillspot.chat_message ADD COLUMN is_read BOOLEAN NOT NULL DEFAULT false;

-- Update existing rows for safety (though default false should handle it)
UPDATE skillspot.chat_message SET is_read = false;

-- Add indexes for performance
CREATE INDEX idx_chat_message_thread_is_read ON skillspot.chat_message(thread_id, is_read);
CREATE INDEX idx_chat_message_sender_sub ON skillspot.chat_message(sender_sub);
