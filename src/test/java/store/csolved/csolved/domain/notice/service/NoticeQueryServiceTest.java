package store.csolved.csolved.domain.notice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.domain.notice.exception.NoticeNotFoundException;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;
import store.csolved.csolved.domain.notice.service.result.NoticeCardResult;
import store.csolved.csolved.domain.notice.service.result.NoticeDetailResult;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeQueryServiceTest
{
    @Mock
    private NoticeMapper noticeMapper;

    @InjectMocks
    private NoticeQueryService noticeQueryService;

    @Test
    @DisplayName("공지사항 목록을 성공적으로 조회한다")
    void getNotices_Success()
    {
        //given
        Pagination pagination = Pagination.create(1L, 10L);
        Searching searching = Searching.create("TITLE", "Test");
        List<NoticeCardRecord> noticeCardRecords = List.of(
                createNoticeCardRecord(1L, "Test Title 1"),
                createNoticeCardRecord(2L, "Test Title 2")
        );

        when(noticeMapper.getNotices(pagination, searching)).thenReturn(noticeCardRecords);

        //when
        List<NoticeCardResult> result = noticeQueryService.getNotices(pagination, searching);

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Title 1", result.get(0).getTitle());
        assertEquals("Test Title 2", result.get(1).getTitle());
        verify(noticeMapper).getNotices(pagination, searching);
    }

    @Test
    @DisplayName("빈 공지사항 목록을 성공적으로 조회한다")
    void getNotices_Success_EmptyList()
    {
        //given
        Pagination pagination = Pagination.create(1L, 0L);
        Searching searching = Searching.create("title", "nonexistent");
        List<NoticeCardRecord> emptyList = List.of();

        when(noticeMapper.getNotices(pagination, searching)).thenReturn(emptyList);

        //when
        List<NoticeCardResult> result = noticeQueryService.getNotices(pagination, searching);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(noticeMapper).getNotices(pagination, searching);
    }

    @Test
    @DisplayName("공지사항 상세 조회 시 조회수가 증가하고 결과를 반환한다")
    void getNoticeWithIncreaseView_Success()
    {
        //given
        Long noticeId = 1L;
        NoticeDetailRecord noticeDetailRecord = createNoticeDetailRecord(noticeId, "Test Notice");

        when(noticeMapper.getNotice(noticeId)).thenReturn(noticeDetailRecord);

        //when
        NoticeDetailResult result = noticeQueryService.getNoticeWithIncreaseView(noticeId);

        //then
        assertNotNull(result);
        assertEquals(noticeId, result.getId());
        assertEquals("Test Notice", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertEquals(1L, result.getAuthorId());
        assertEquals("testUser", result.getAuthorNickname());
        assertEquals(100L, result.getViews());

        verify(noticeMapper).getNotice(noticeId);
        verify(noticeMapper).increaseView(noticeId);
    }

    @Test
    @DisplayName("존재하지 않는 공지사항을 조회하면 NoticeNotFoundException이 발생한다")
    void getNoticeWithIncreaseView_ThrowsNoticeNotFoundException_WhenNoticeNotFound()
    {
        //given
        Long noticeId = 999L;

        when(noticeMapper.getNotice(noticeId)).thenReturn(null);

        //when & then
        assertThrows(NoticeNotFoundException.class, () ->
        {
            noticeQueryService.getNoticeWithIncreaseView(noticeId);
        });

        verify(noticeMapper).getNotice(noticeId);
        verify(noticeMapper, never()).increaseView(any());
    }

    @Test
    @DisplayName("공지사항 총 개수를 성공적으로 조회한다")
    void countNotices_Success()
    {
        //given
        Searching searching = Searching.create("title", "test");
        Long expectedCount = 5L;

        when(noticeMapper.countNotices(searching)).thenReturn(expectedCount);

        //when
        Long actualCount = noticeQueryService.countNotices(searching);

        //then
        assertEquals(expectedCount, actualCount);
        verify(noticeMapper).countNotices(searching);
    }

    @Test
    @DisplayName("검색 조건에 맞는 공지사항이 없을 때 0을 반환한다")
    void countNotices_Success_ZeroCount()
    {
        //given
        Searching searching = Searching.create("title", "nonexistent");
        Long expectedCount = 0L;

        when(noticeMapper.countNotices(searching)).thenReturn(expectedCount);

        //when
        Long actualCount = noticeQueryService.countNotices(searching);

        //then
        assertEquals(expectedCount, actualCount);
        verify(noticeMapper).countNotices(searching);
    }

    private NoticeCardRecord createNoticeCardRecord(Long id, String title)
    {
        return NoticeCardRecord.builder()
                .id(id)
                .title(title)
                .authorId(1L)
                .authorNickname("testUser")
                .views(50L)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    private NoticeDetailRecord createNoticeDetailRecord(Long id, String title)
    {
        return NoticeDetailRecord.builder()
                .id(id)
                .title(title)
                .authorId(1L)
                .authorNickname("testUser")
                .content("Test Content")
                .views(100L)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}