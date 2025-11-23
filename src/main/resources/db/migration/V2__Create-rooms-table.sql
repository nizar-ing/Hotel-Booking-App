-- Create rooms table
CREATE TABLE rooms
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number  INT            NOT NULL UNIQUE,
    room_type    VARCHAR(50)    NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    availability BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at   DATE                    DEFAULT (CURRENT_DATE),
    image_url    VARCHAR(500),

    INDEX idx_room_type (room_type),
    INDEX idx_availability (availability),
    INDEX idx_price (price)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;