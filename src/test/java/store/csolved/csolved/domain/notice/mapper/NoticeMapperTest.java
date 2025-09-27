package store.csolved.csolved.domain.notice.mapper;

import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;
import store.csolved.csolved.global.utils.page.Pagination;
import store.csolved.csolved.global.utils.search.Searching;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class NoticeMapperTest
{
    @Autowired
    NoticeMapper noticeMapper;

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

    }

    @AfterAll
    void cleanUp()
    {
        // FK 제약조건을 고려하여 역순으로 삭제
        jdbcTemplate.execute("DELETE FROM notice");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    @DisplayName("공지사항을 저장하고 조회할 수 있다")
    void saveAndGetNotice()
    {
        //given
        Notice notice = createNotice("테스트 공지사항", "테스트 내용", 1L, false);
        noticeMapper.saveNotice(notice);

        //when
        NoticeDetailRecord foundNotice = noticeMapper.getNotice(notice.getId());

        //then
        assertThat(foundNotice.getTitle()).isEqualTo(notice.getTitle());
        assertThat(foundNotice.getContent()).isEqualTo(notice.getContent());
        assertThat(foundNotice.getAuthorId()).isEqualTo(notice.getAuthorId());
    }

    @Test
    @DisplayName("여러 공지사항을 저장하고 목록으로 조회할 수 있다")
    void saveAndGetNotices()
    {
        //given
        Notice notice1 = createNotice("첫 번째 공지", "첫 번째 내용", 1L, true);
        Notice notice2 = createNotice("두 번째 공지", "두 번째 내용", 2L, false);
        Notice notice3 = createNotice("세 번째 공지", "세 번째 내용", 3L, false);

        noticeMapper.saveNotice(notice1);
        noticeMapper.saveNotice(notice2);
        noticeMapper.saveNotice(notice3);

        Pagination page = Pagination.create(1L, 10L);
        Searching search = Searching.create(null, null);

        //when
        List<NoticeCardRecord> notices = noticeMapper.getNotices(page, search);

        //then
        assertThat(notices).hasSize(3);
    }

    @Test
    @DisplayName("공지사항을 수정할 수 있다")
    void updateNotice()
    {
        //given
        Notice notice = createNotice("원본 제목", "원본 내용", 1L, false);
        noticeMapper.saveNotice(notice);

        Notice updateInfo = Notice.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .isPinned(true)
                .build();

        //when
        noticeMapper.updateNotice(notice.getId(), updateInfo);

        //then
        NoticeDetailRecord updatedNotice = noticeMapper.getNotice(notice.getId());
        assertThat(updatedNotice.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedNotice.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    @DisplayName("공지사항을 삭제할 수 있다")
    void deleteNotice()
    {
        //given
        Notice notice = createNotice("삭제할 공지", "삭제될 내용", 1L, false);
        noticeMapper.saveNotice(notice);

        //when
        noticeMapper.deleteNotice(notice.getId());

        //then
        NoticeDetailRecord deletedNotice = noticeMapper.getNotice(notice.getId());
        assertThat(deletedNotice).isNull();
    }

    @Test
    @DisplayName("공지사항 개수를 조회할 수 있다")
    void countNotices()
    {
        //given
        noticeMapper.saveNotice(createNotice("공지1", "내용1", 1L, false));
        noticeMapper.saveNotice(createNotice("공지2", "내용2", 2L, false));

        Searching search = Searching.create(null, null);

        //when
        Long count = noticeMapper.countNotices(search);

        //then
        assertThat(count).isEqualTo(2L);
    }

    @Test
    @DisplayName("공지사항의 조회수를 증가시킬 수 있다")
    void increaseView()
    {
        //given
        Notice notice = createNotice("조회수 테스트", "조회수 테스트 내용", 1L, false);
        noticeMapper.saveNotice(notice);

        NoticeDetailRecord beforeNotice = noticeMapper.getNotice(notice.getId());
        Long initialViews = beforeNotice.getViews();

        //when
        noticeMapper.increaseView(notice.getId());

        //then
        NoticeDetailRecord afterNotice = noticeMapper.getNotice(notice.getId());
        assertThat(afterNotice.getViews()).isEqualTo(initialViews + 1);
    }

    @Test
    @DisplayName("제목으로 공지사항을 검색할 수 있다")
    void searchNoticesByTitle()
    {
        //given
        noticeMapper.saveNotice(createNotice("중요한 공지사항", "중요한 내용", 1L, false));
        noticeMapper.saveNotice(createNotice("일반 공지", "일반 내용", 2L, false));
        noticeMapper.saveNotice(createNotice("긴급 공지사항", "긴급 내용", 3L, false));

        Pagination page = Pagination.create(1L, 10L);
        Searching search = Searching.create("TITLE", "공지사항");

        //when
        List<NoticeCardRecord> searchResults = noticeMapper.getNotices(page, search);

        //then
        assertThat(searchResults).hasSize(2);
        assertThat(searchResults.stream().allMatch(notice ->
                notice.getTitle().contains("공지사항"))).isTrue();
    }

    @Test
    @DisplayName("페이지네이션이 올바르게 동작한다")
    void testPagination()
    {
        //given
        for (int i = 1; i <= 15; i++)
        {
            noticeMapper.saveNotice(createNotice("공지 " + i, "내용 " + i, 1L, false));
        }

        Pagination firstPage = Pagination.create(1L, 10L);
        Pagination secondPage = Pagination.create(2L, 10L);
        Searching search = Searching.create(null, null);

        //when
        List<NoticeCardRecord> firstPageResults = noticeMapper.getNotices(firstPage, search);
        List<NoticeCardRecord> secondPageResults = noticeMapper.getNotices(secondPage, search);

        //then
        assertThat(firstPageResults).hasSize(7);
        assertThat(secondPageResults).hasSize(7);
        assertThat(noticeMapper.countNotices(search)).isEqualTo(15L);
    }

    @Test
    @DisplayName("공지사항 아이디로 글쓴이를 조회할 수 있다.")
    void testGetAuthorId()
    {
        Notice notice1 = createNotice("공지사항1", "다들 점심 먹고와요.", 1L, false);
        Notice notice2 = createNotice("공지사항2", "콜 베이비.", 2L, false);

        noticeMapper.saveNotice(notice1);
        noticeMapper.saveNotice(notice2);

        assertThat(noticeMapper.getAuthorId(notice1.getId())).isEqualTo(1L);
        assertThat(noticeMapper.getAuthorId(notice2.getId())).isEqualTo(2L);
    }

    private Notice createNotice(String title, String content, Long authorId, boolean isPinned)
    {
        return Notice.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .isPinned(isPinned)
                .build();
    }
}