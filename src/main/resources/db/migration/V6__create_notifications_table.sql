CREATE TABLE notifications
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject           VARCHAR(255),
    recipient         VARCHAR(255) NOT NULL,
    body              TEXT,
    booking_reference VARCHAR(255),
    type              VARCHAR(50)  NOT NULL,
    created_at        DATE DEFAULT (CURDATE()),

    INDEX idx_recipient (recipient),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_type (type),
    INDEX idx_created_at (created_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;