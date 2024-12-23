-- 사용자 정보
CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(200) NOT NULL,
    nickname   VARCHAR(50)  NOT NULL,
    company    VARCHAR(100),
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_admin   BOOLEAN               DEFAULT FALSE deleted_at TIMESTAMP NULL;
);

-- CS 질문
CREATE TABLE questions
(
    id         BIGINT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    content    TEXT         NOT NULL,
    category   VARCHAR(50)  NOT NULL, -- OS, Network, DB 등
    views      INT                   DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 질문 태그
CREATE TABLE question_tags
(
    id          BIGINT PRIMARY KEY,
    question_id BIGINT      NOT NULL,
    tag_name    VARCHAR(50) NOT NULL,
    deleted_at  TIMESTAMP NULL,
    FOREIGN KEY (question_id) REFERENCES questions (id)
);

-- 답변
CREATE TABLE answers
(
    id          BIGINT PRIMARY KEY,
    question_id BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    content     TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP NULL,
    FOREIGN KEY (question_id) REFERENCES questions (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 답변 평가
CREATE TABLE answer_ratings
(
    id         BIGINT PRIMARY KEY,
    answer_id  BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    score      INT       NOT NULL CHECK (score BETWEEN 1 AND 5),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (answer_id) REFERENCES answers (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (answer_id, user_id) -- 한 사용자가 하나의 답변에 한 번만 평가 가능
);

-- 댓글 (답변에 대한)
CREATE TABLE comments
(
    id                BIGINT PRIMARY KEY,
    answer_id         BIGINT    NOT NULL,
    user_id           BIGINT    NOT NULL,
    content           TEXT      NOT NULL,
    parent_comment_id BIGINT, -- 대댓글인 경우 부모 댓글 ID
    created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at        TIMESTAMP NULL,
    FOREIGN KEY (answer_id) REFERENCES answers (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (parent_comment_id) REFERENCES comments (id)
);

-- 퀴즈 세트
CREATE TABLE quiz_sets
(
    id         BIGINT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    difficulty VARCHAR(20)  NOT NULL, -- 초급, 중급, 고급
    views      INT                   DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 퀴즈 문제
CREATE TABLE quiz_questions
(
    id            BIGINT PRIMARY KEY,
    quiz_set_id   BIGINT    NOT NULL,
    question_text TEXT      NOT NULL,
    explanation   TEXT      NOT NULL, -- 해설
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP NULL,
    FOREIGN KEY (quiz_set_id) REFERENCES quiz_sets (id)
);

-- 퀴즈 보기
CREATE TABLE quiz_options
(
    id            BIGINT PRIMARY KEY,
    question_id   BIGINT  NOT NULL,
    option_number CHAR(1) NOT NULL, -- A, B, C, D
    option_text   TEXT    NOT NULL,
    is_correct    BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES quiz_questions (id)
);

-- 퀴즈 결과
CREATE TABLE quiz_results
(
    id           BIGINT PRIMARY KEY,
    quiz_set_id  BIGINT    NOT NULL,
    user_id      BIGINT    NOT NULL,
    score        INT       NOT NULL,
    time_taken   INT       NOT NULL, -- 초 단위
    completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_set_id) REFERENCES quiz_sets (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 신고 테이블 (질문, 답변, 퀴즈 모두에 대한 신고 처리)
CREATE TABLE reports
(
    id          BIGINT PRIMARY KEY,
    reporter_id BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,                   -- QUESTION, ANSWER, QUIZ
    target_id   BIGINT      NOT NULL,
    reason      TEXT        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, REVIEWED
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users (id)
);