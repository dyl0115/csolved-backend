CREATE TABLE bookmarks
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    post_id     BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL,
    deleted_at  TIMESTAMP NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    INDEX idx_user_post (user_id, post_id),
    INDEX idx_deleted_at (deleted_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;