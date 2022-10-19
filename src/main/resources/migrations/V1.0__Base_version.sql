
CREATE TABLE IF NOT EXISTS notifications
(
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id         INTEGER NOT NULL,
    timestamp       INTEGER NOT NULL,
    is_read         BOOLEAN NOT NULL,
    message         VARCHAR(255) NOT NULL
);

CREATE INDEX idx_notifications_user_id
    ON notifications (user_id);

CREATE INDEX idx_notifications_timestamp
    ON notifications (timestamp);