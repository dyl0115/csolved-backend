-- 테스트용 기본 데이터

-- 기본 사용자 데이터
INSERT INTO users (email, password, nickname, company, admin, created_at)
VALUES ('test1@example.com', 'password123', 'testUser1', 'testCompany', false, CURRENT_TIMESTAMP),
       ('test2@example.com', 'password123', 'testUser2', 'testCompany', false, CURRENT_TIMESTAMP),
       ('admin@example.com', 'password123', 'adminUser', 'testCompany', true, CURRENT_TIMESTAMP);

-- 기본 카테고리 데이터
INSERT INTO category (post_type, name, created_at)
VALUES (1, '일반질문', CURRENT_TIMESTAMP),
       (1, '기술토론', CURRENT_TIMESTAMP),
       (2, '커뮤니티', CURRENT_TIMESTAMP);

-- 기본 태그 데이터
INSERT INTO tags (name, created_at)
VALUES ('Java', CURRENT_TIMESTAMP),
       ('Spring', CURRENT_TIMESTAMP),
       ('MySQL', CURRENT_TIMESTAMP),
       ('Test', CURRENT_TIMESTAMP);