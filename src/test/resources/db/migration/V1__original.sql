CREATE TABLE users
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    profile_image VARCHAR(2083)         DEFAULT NULL,
    email         VARCHAR(100) NOT NULL,
    password      VARCHAR(200) NOT NULL,
    nickname      VARCHAR(50)  NOT NULL,
    company       VARCHAR(100)          DEFAULT NULL,
    admin         BOOLEAN               DEFAULT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP    NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email),
    CONSTRAINT uk_nickname UNIQUE (nickname)
);

CREATE TABLE category
(
    id         BIGINT    NOT NULL AUTO_INCREMENT,
    post_type  INTEGER        DEFAULT NULL,
    name       VARCHAR(100)   DEFAULT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
);

CREATE TABLE posts
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    post_type    INTEGER      NOT NULL,
    user_id      BIGINT       NOT NULL,
    anonymous    BOOLEAN               DEFAULT FALSE,
    title        VARCHAR(200) NOT NULL,
    content CLOB NOT NULL,
    category_id  BIGINT                DEFAULT NULL,
    views        INTEGER               DEFAULT 0,
    likes        BIGINT                DEFAULT 0,
    answer_count BIGINT                DEFAULT 0,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP    NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT posts_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE tags
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT tag_name UNIQUE (name)
);

CREATE TABLE post_tags
(
    post_id BIGINT NOT NULL,
    tag_id  BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    CONSTRAINT post_tags_ibfk_1 FOREIGN KEY (post_id) REFERENCES posts (id),
    CONSTRAINT post_tags_ibfk_2 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

CREATE TABLE post_likes
(
    id         BIGINT    NOT NULL AUTO_INCREMENT,
    post_id    BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_question_likes UNIQUE (post_id, user_id),
    CONSTRAINT fk_question_likes_question FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_question_likes_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE code_reviews
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    post_id    BIGINT       NOT NULL,
    github_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT unique_post_id UNIQUE (post_id),
    CONSTRAINT code_reviews_ibfk_1 FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE TABLE answers
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    post_id     BIGINT    NOT NULL,
    author_id   BIGINT    NOT NULL,
    anonymous   BOOLEAN            DEFAULT NULL,
    content CLOB NOT NULL,
    total_score DOUBLE             DEFAULT 0,
    voter_count BIGINT             DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT answers_ibfk_2 FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE answer_ratings
(
    id         BIGINT    NOT NULL AUTO_INCREMENT,
    answer_id  BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    score      INTEGER   NOT NULL CHECK (score BETWEEN 1 AND 5),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_answer_rating UNIQUE (answer_id, user_id),
    CONSTRAINT answer_ratings_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answers (id),
    CONSTRAINT answer_ratings_ibfk_2 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE comments
(
    id         BIGINT    NOT NULL AUTO_INCREMENT,
    answer_id  BIGINT    NOT NULL,
    author_id  BIGINT    NOT NULL,
    anonymous  BOOLEAN            DEFAULT FALSE,
    content CLOB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT comments_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answers (id),
    CONSTRAINT comments_ibfk_2 FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE reports
(
    id          BIGINT      NOT NULL,
    reporter_id BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id   BIGINT      NOT NULL,
    reason CLOB NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT reports_ibfk_1 FOREIGN KEY (reporter_id) REFERENCES users (id)
);