# 1.comments 테이블에 post_id 칼럼을 추가.
ALTER TABLE comments
    ADD COLUMN post_id BIGINT AFTER id;

# 2. comments 테이블의 각 행이 가진 answer_id를 통해 매칭되는 post_id를 가져와서 업데이트
UPDATE comments c
SET post_id = (SELECT a.post_id
               FROM answers a
               WHERE a.id = c.answer_id);

# 3. comments 테이블의 post_id에 외래키 조건을 추가.
ALTER TABLE comments
    ADD CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id);