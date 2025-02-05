CREATE TABLE users
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(200) NOT NULL,
    nickname   VARCHAR(50)  NOT NULL,
    company    VARCHAR(100)          DEFAULT NULL,
    is_admin   BOOLEAN               DEFAULT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);

CREATE TABLE questions
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    content    CLOB         NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    views      INT                   DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT questions_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE question_tags
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    question_id BIGINT      NOT NULL,
    tag_name    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT question_tags_question_fk FOREIGN KEY (question_id) REFERENCES questions (id)
);

CREATE TABLE answers
(
    id          BIGINT    NOT NULL AUTO_INCREMENT,
    question_id BIGINT    NOT NULL,
    user_id     BIGINT    NOT NULL,
    content     CLOB      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT answers_question_fk FOREIGN KEY (question_id) REFERENCES questions (id),
    CONSTRAINT answers_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE answer_ratings
(
    id         BIGINT    NOT NULL AUTO_INCREMENT,
    answer_id  BIGINT    NOT NULL,
    user_id    BIGINT    NOT NULL,
    score      INT       NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT answer_ratings_unique UNIQUE (answer_id, user_id),
    CONSTRAINT answer_ratings_answer_fk FOREIGN KEY (answer_id) REFERENCES answers (id),
    CONSTRAINT answer_ratings_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT answer_ratings_score_check CHECK (score BETWEEN 1 AND 5)
);

CREATE TABLE comments
(
    id                BIGINT    NOT NULL AUTO_INCREMENT,
    answer_id         BIGINT    NOT NULL,
    user_id           BIGINT    NOT NULL,
    content           CLOB      NOT NULL,
    parent_comment_id BIGINT             DEFAULT NULL,
    created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at        TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT comments_answer_fk FOREIGN KEY (answer_id) REFERENCES answers (id),
    CONSTRAINT comments_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT comments_parent_fk FOREIGN KEY (parent_comment_id) REFERENCES comments (id)
);

CREATE TABLE quiz_sets
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(200) NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    difficulty VARCHAR(20)  NOT NULL,
    views      INT                   DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT quiz_sets_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE quiz_questions
(
    id            BIGINT    NOT NULL AUTO_INCREMENT,
    quiz_set_id   BIGINT    NOT NULL,
    question_text CLOB      NOT NULL,
    explanation   CLOB      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT quiz_questions_quiz_set_fk FOREIGN KEY (quiz_set_id) REFERENCES quiz_sets (id)
);

CREATE TABLE quiz_options
(
    id            BIGINT  NOT NULL AUTO_INCREMENT,
    question_id   BIGINT  NOT NULL,
    option_number CHAR(1) NOT NULL,
    option_text   CLOB    NOT NULL,
    is_correct    BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT quiz_options_question_fk FOREIGN KEY (question_id) REFERENCES quiz_questions (id)
);

CREATE TABLE quiz_results
(
    id           BIGINT    NOT NULL AUTO_INCREMENT,
    quiz_set_id  BIGINT    NOT NULL,
    user_id      BIGINT    NOT NULL,
    score        INT       NOT NULL,
    time_taken   INT       NOT NULL,
    completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT quiz_results_quiz_set_fk FOREIGN KEY (quiz_set_id) REFERENCES quiz_sets (id),
    CONSTRAINT quiz_results_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE reports
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    reporter_id BIGINT      NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id   BIGINT      NOT NULL,
    reason      CLOB        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT reports_reporter_fk FOREIGN KEY (reporter_id) REFERENCES users (id)
);