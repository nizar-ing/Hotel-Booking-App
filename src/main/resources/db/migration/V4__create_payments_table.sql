CREATE TABLE payments
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id    VARCHAR(255)   NOT NULL,
    amount            DECIMAL(19, 2) NOT NULL,
    payment_gateway   VARCHAR(50)    NOT NULL,
    payment_date      DATE DEFAULT (CURDATE()),
    payment_status    VARCHAR(50)    NOT NULL,
    booking_reference VARCHAR(255)   NOT NULL,
    failure_reason    VARCHAR(500),
    user_id           BIGINT         NOT NULL,

    CONSTRAINT fk_payment_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE RESTRICT,

    UNIQUE INDEX uk_transaction_id (transaction_id),
    INDEX idx_user_id (user_id),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_date (payment_date)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;