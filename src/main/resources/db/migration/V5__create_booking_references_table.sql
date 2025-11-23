CREATE TABLE booking_references
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_no VARCHAR(255) NOT NULL,

    CONSTRAINT uk_reference_no UNIQUE (reference_no)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;