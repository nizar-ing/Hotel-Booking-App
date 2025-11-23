
CREATE TABLE bookings
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT         NOT NULL,
    room_id           BIGINT         NOT NULL,
    payment_status    VARCHAR(50)    NOT NULL,
    check_in_date     DATE           NOT NULL,
    check_out_date    DATE           NOT NULL,
    total_price       DECIMAL(19, 2) NOT NULL,
    booking_reference VARCHAR(255)   NOT NULL,
    created_at        DATE DEFAULT (CURRENT_DATE),
    booking_status    VARCHAR(50)    NOT NULL,

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_booking_room
        FOREIGN KEY (room_id)
            REFERENCES rooms (id),

    INDEX idx_user_id (user_id),
    INDEX idx_room_id (room_id),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_check_in_date (check_in_date),
    INDEX idx_booking_status (booking_status)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;