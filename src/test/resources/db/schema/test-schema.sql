-- H2 호환 테스트 스키마
-- MySQL의 tinyint를 boolean으로, bigint를 BIGINT로 변경

DROP TABLE IF EXISTS answer_ratings;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS bookmarks;
DROP TABLE IF EXISTS post_likes;
DROP TABLE IF EXISTS post_tags;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

-- users 테이블
CREATE TABLE users
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    profile_image VARCHAR(2083),
    email         VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(200) NOT NULL,
    nickname      VARCHAR(50)  NOT NULL UNIQUE,
    company       VARCHAR(100),
    admin         BOOLEAN   DEFAULT FALSE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at   TIMESTAMP,
    deleted_at    TIMESTAMP
);

-- category 테이블
CREATE TABLE category
(
    id          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_type   TINYINT,
    name        VARCHAR(100) UNIQUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    deleted_at  TIMESTAMP
);

-- posts 테이블
CREATE TABLE posts
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    post_type    TINYINT      NOT NULL,
    user_id      BIGINT       NOT NULL,
    anonymous    BOOLEAN               DEFAULT FALSE,
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    category_id  BIGINT                DEFAULT NULL,
    views        INT                   DEFAULT 0,
    likes        BIGINT                DEFAULT 0,
    answer_count BIGINT                DEFAULT 0,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at  TIMESTAMP    NULL     DEFAULT NULL,
    deleted_at   TIMESTAMP    NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT posts_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category (id)
);

-- tags 테이블
CREATE TABLE tags
(
    id          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) UNIQUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    deleted_at  TIMESTAMP
);

-- answers 테이블
CREATE TABLE answers
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    post_id     BIGINT    NOT NULL,
    author_id   BIGINT    NOT NULL,
    anonymous   BOOLEAN            DEFAULT NULL,
    content     TEXT      NOT NULL,
    total_score FLOAT              DEFAULT 0,
    voter_count BIGINT             DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL     DEFAULT NULL,
    deleted_at  TIMESTAMP NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT answers_ibfk_2 FOREIGN KEY (author_id) REFERENCES users (id)
);

-- post_tags 테이블
CREATE TABLE post_tags
(
    post_id    BIGINT NOT NULL,
    tag_id     BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);

-- post_likes 테이블
CREATE TABLE post_likes
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- bookmarks 테이블
CREATE TABLE bookmarks
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    post_id     BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL     DEFAULT NULL,
    deleted_at  TIMESTAMP NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT bookmarks_ibfk_1 FOREIGN KEY (post_id) REFERENCES posts (id),
    CONSTRAINT bookmarks_ibfk_2 FOREIGN KEY (user_id) REFERENCES users (id)
);

-- comments 테이블
CREATE TABLE comments
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    post_id     BIGINT             DEFAULT NULL,
    answer_id   BIGINT    NOT NULL,
    author_id   BIGINT    NOT NULL,
    anonymous   BOOLEAN            DEFAULT FALSE,
    content     TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL     DEFAULT NULL,
    deleted_at  TIMESTAMP NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT comments_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answers (id),
    CONSTRAINT comments_ibfk_2 FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- answer_ratings 테이블
CREATE TABLE answer_ratings
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    answer_id  BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    score      INT    NOT NULL CHECK (score BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (answer_id, user_id),
    FOREIGN KEY (answer_id) REFERENCES answers (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- reports 테이블
CREATE TABLE reports
(
    id          BIGINT      NOT NULL,
    reporter_id BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id   BIGINT      NOT NULL,
    reason      TEXT        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP   NULL     DEFAULT NULL,
    deleted_at  TIMESTAMP   NULL     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT reports_ibfk_1 FOREIGN KEY (reporter_id) REFERENCES users (id)
);

-- notice 테이블
CREATE TABLE notice
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    author_id   BIGINT       NOT NULL REFERENCES User (id),

    -- 공지사항 특화 컬럼들
    is_pinned   BOOLEAN   DEFAULT FALSE, -- 상단 고정

    -- 기본 관리
    views       INTEGER   DEFAULT 0,     -- 조회수
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP DEFAULT NULL
);