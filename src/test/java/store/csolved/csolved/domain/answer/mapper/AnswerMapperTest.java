package store.csolved.csolved.domain.answer.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;

import java.time.LocalDateTime;
import java.util.List;


@MybatisTest
// 기본값(PER_METHOD)은 매 테스트마다 항상 새로운 인스턴스(answerMapper, jdbcTemplate)를 주입받음.
// PER_CLASS는 클래스가 생성될 때 딱 최초 한번만 인스턴스를 주입받는다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 테스트 간 격리성 보장 + 자동롤백 (Service의 @Transactional과 다르게 동작)
@Transactional
class AnswerMapperTest
{
    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp()
    {
        // 사용자 데이터
        jdbcTemplate.execute(
                "INSERT INTO users (id, email, password, nickname, company, admin, created_at)\n" +
                        "VALUES (1, 'test1@example.com', 'password123', 'testUser1', 'testCompany', false, CURRENT_TIMESTAMP),\n" +
                        "       (2, 'test2@example.com', 'password123', 'testUser2', 'testCompany', false, CURRENT_TIMESTAMP),\n" +
                        "       (3, 'admin@example.com', 'password123', 'adminUser', 'testCompany', true, CURRENT_TIMESTAMP);");

        // 카테고리 데이터
        jdbcTemplate.execute(
                "INSERT INTO category (id, post_type, name, created_at)\n" +
                        "VALUES (1, 1, '일반질문', CURRENT_TIMESTAMP),\n" +
                        "       (2, 1, '기술토론', CURRENT_TIMESTAMP),\n" +
                        "       (3, 2, '커뮤니티', CURRENT_TIMESTAMP);");

        // 태그 데이터
        jdbcTemplate.execute(
                "INSERT INTO tags (id, name, created_at)\n" +
                        "VALUES (1, 'Java', CURRENT_TIMESTAMP),\n" +
                        "       (2, 'Spring', CURRENT_TIMESTAMP),\n" +
                        "       (3, 'MySQL', CURRENT_TIMESTAMP),\n" +
                        "       (4, 'Test', CURRENT_TIMESTAMP);");

        // 게시글 데이터
        jdbcTemplate.execute(
                "INSERT INTO posts(id, post_type, user_id, " +
                        "anonymous, title, content, " +
                        "category_id, views, likes, " +
                        "answer_count, created_at) " +
                        "VALUES (1, 1, 1, true, 'Title1', 'Content1', 1, 0,0,0,'2025-02-16 22:20:49'), " +
                        "(2, 1, 1, true, 'Title2', 'Content2', 1, 0,0,0,'2025-02-11 22:20:49')");

    }

    @Test
    @DisplayName("대댓글이 달린 댓글 조회 테스트 ")
    void getAnswersWithComments()
    {
        //given
        Long postId = 1L;
        Long davidId = 1L;
        Answer answer1 = createTestAnswer(null, davidId, postId, "Answer1");
        answerMapper.saveAnswer(answer1);

        // 실제 생성된 answer ID를 가져와서 comment 데이터 추가
        Long answerId1 = answer1.getId();

        jdbcTemplate.execute(
                "INSERT INTO comments(answer_id, author_id, anonymous, content, created_at) " +
                "VALUES (" + answerId1 + ", 1, false, 'Comment1', CURRENT_TIMESTAMP)");

        //when
        List<AnswerWithCommentsRecord> answersWithComments = answerMapper.getAnswersWithComments(postId);
//        System.out.println("=== With Comments ===");
//        answersWithComments.forEach(System.out::println);

        //then
        Assertions.assertThat(answersWithComments.size()).isEqualTo(1);
        Assertions.assertThat(answersWithComments.get(0).getComments().size()).isEqualTo(1);
    }

    @Test
    void getAnswersWithoutComments()
    {
        //given
        Long postId = 1L;
        Long davidId = 1L;
        Answer answer1 = createTestAnswer(null, davidId, postId, "Answer1");
        Answer answer2 = createTestAnswer(null, davidId, postId, "Answer2");
        answerMapper.saveAnswer(answer1);
        answerMapper.saveAnswer(answer2);

        //when
        List<AnswerWithCommentsRecord> answersWithComments = answerMapper.getAnswersWithComments(postId);
//        System.out.println("=== Without Comments ===");
//        answersWithComments.forEach(System.out::println);

        //then
        Assertions.assertThat(answersWithComments.size()).isEqualTo(2);
        Assertions.assertThat(answersWithComments.get(0).getComments().size()).isEqualTo(0);
        Assertions.assertThat(answersWithComments.get(1).getComments().size()).isEqualTo(0);
    }

    private Answer createTestAnswer(Long id, Long authorId, Long postId, String content)
    {
        return Answer.builder()
                .id(id)
                .postId(postId)
                .authorId(authorId)
                .anonymous(false)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}