CREATE TABLE `users`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `profile_image` varchar(2083)         DEFAULT NULL,
    `email`         varchar(100) NOT NULL,
    `password`      varchar(200) NOT NULL,
    `nickname`      varchar(50)  NOT NULL,
    `company`       varchar(100)          DEFAULT NULL,
    `admin`         tinyint(1)            DEFAULT NULL,
    `created_at`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`    timestamp    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `uk_nickname` (`nickname`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 118
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `category`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT,
    `post_type`  tinyint        DEFAULT NULL,
    `name`       varchar(100)   DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `posts`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `post_type`    tinyint      NOT NULL,
    `user_id`      bigint       NOT NULL,
    `anonymous`    tinyint(1)            DEFAULT '0',
    `title`        varchar(200) NOT NULL,
    `content`      text         NOT NULL,
    `category_id`  bigint                DEFAULT NULL,
    `views`        int                   DEFAULT '0',
    `likes`        bigint                DEFAULT '0',
    `answer_count` bigint                DEFAULT '0',
    `created_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`   timestamp    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `fk_category` (`category_id`),
    CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
    CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 116
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tags`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `tag_name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 113
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `post_tags`
(
    `post_id` bigint NOT NULL,
    `tag_id`  bigint NOT NULL,
    PRIMARY KEY (`post_id`, `tag_id`),
    KEY `question_tags_ibfk_2` (`tag_id`),
    CONSTRAINT `post_tags_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
    CONSTRAINT `post_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `post_likes`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT,
    `post_id`    bigint    NOT NULL,
    `user_id`    bigint    NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_question_likes` (`post_id`, `user_id`),
    KEY `fk_question_likes_user` (`user_id`),
    CONSTRAINT `fk_question_likes_question` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_question_likes_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `code_reviews`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `post_id`    bigint       NOT NULL,
    `github_url` varchar(255) NOT NULL,
    `created_at` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_post_id` (`post_id`),
    CONSTRAINT `code_reviews_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `answers`
(
    `id`          bigint    NOT NULL AUTO_INCREMENT,
    `post_id`     bigint    NOT NULL,
    `author_id`   bigint    NOT NULL,
    `anonymous`   tinyint(1)         DEFAULT NULL,
    `content`     text      NOT NULL,
    `total_score` float              DEFAULT '0',
    `voter_count` bigint             DEFAULT '0',
    `created_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `question_id` (`post_id`),
    KEY `user_id` (`author_id`),
    CONSTRAINT `answers_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `answer_ratings`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT,
    `answer_id`  bigint    NOT NULL,
    `user_id`    bigint    NOT NULL,
    `score`      int       NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `answer_id` (`answer_id`, `user_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `answer_ratings_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`),
    CONSTRAINT `answer_ratings_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `answer_ratings_chk_1` CHECK ((`score` between 1 and 5))
) ENGINE = InnoDB
  AUTO_INCREMENT = 57
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `comments`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT,
    `answer_id`  bigint    NOT NULL,
    `author_id`  bigint    NOT NULL,
    `anonymous`  tinyint(1)         DEFAULT '0',
    `content`    text      NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `answer_id` (`answer_id`),
    KEY `user_id` (`author_id`),
    CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`),
    CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `reports`
(
    `id`          bigint      NOT NULL,
    `reporter_id` bigint      NOT NULL,
    `target_type` varchar(20) NOT NULL,
    `target_id`   bigint      NOT NULL,
    `reason`      text        NOT NULL,
    `status`      varchar(20) NOT NULL DEFAULT 'PENDING',
    `created_at`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `reporter_id` (`reporter_id`),
    CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`reporter_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;