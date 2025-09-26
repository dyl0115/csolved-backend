package store.csolved.csolved.domain.notice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.domain.notice.exception.NoticeAdminOnlyException;
import store.csolved.csolved.domain.notice.exception.NoticeNotFoundException;
import store.csolved.csolved.domain.notice.exception.NoticeSaveDeniedException;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.service.command.NoticeCreateCommand;
import store.csolved.csolved.domain.notice.service.command.NoticeUpdateCommand;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NoticeCommandService 테스트")
class NoticeCommandServiceTest {

    @Mock
    private NoticeMapper noticeMapper;

    @InjectMocks
    private NoticeCommandService noticeCommandService;

    @Test
    @DisplayName("관리자가 공지사항을 성공적으로 저장한다")
    void save_Success_WhenUserIsAdmin() {
        // given
        boolean userIsAdmin = true;
        NoticeCreateCommand command = createNoticeCreateCommand();

        // when
        noticeCommandService.save(userIsAdmin, command);

        // then
        verify(noticeMapper).saveNotice(Notice.from(command));
    }

    @Test
    @DisplayName("관리자가 아닌 사용자가 공지사항을 저장하려 하면 NoticeAdminOnlyException이 발생한다")
    void save_ThrowsNoticeAdminOnlyException_WhenUserIsNotAdmin() {
        // given
        boolean userIsAdmin = false;
        NoticeCreateCommand command = createNoticeCreateCommand();

        // when & then
        assertThrows(NoticeAdminOnlyException.class, () -> {
            noticeCommandService.save(userIsAdmin, command);
        });

        verify(noticeMapper, never()).saveNotice(any());
    }

    @Test
    @DisplayName("관리자가 본인의 공지사항을 성공적으로 업데이트한다")
    void update_Success_WhenUserIsAdminAndAuthor() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;
        NoticeUpdateCommand command = createNoticeUpdateCommand();

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(userId);

        // when
        noticeCommandService.update(userId, userIsAdmin, noticeId, command);

        // then
        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper).updateNotice(noticeId, Notice.from(command));
    }

    @Test
    @DisplayName("존재하지 않는 공지사항을 업데이트하려 하면 NoticeNotFoundException이 발생한다")
    void update_ThrowsNoticeNotFoundException_WhenNoticeNotFound() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;
        NoticeUpdateCommand command = createNoticeUpdateCommand();

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(null);

        // when & then
        assertThrows(NoticeNotFoundException.class, () -> {
            noticeCommandService.update(userId, userIsAdmin, noticeId, command);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).updateNotice(any(), any());
    }

    @Test
    @DisplayName("다른 사용자의 공지사항을 업데이트하려 하면 NoticeSaveDeniedException이 발생한다")
    void update_ThrowsNoticeSaveDeniedException_WhenUserIsNotAuthor() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;
        Long differentAuthorId = 2L;
        NoticeUpdateCommand command = createNoticeUpdateCommand();

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(differentAuthorId);

        // when & then
        assertThrows(NoticeSaveDeniedException.class, () -> {
            noticeCommandService.update(userId, userIsAdmin, noticeId, command);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).updateNotice(any(), any());
    }

    @Test
    @DisplayName("관리자가 아닌 사용자가 공지사항을 업데이트하려 하면 NoticeAdminOnlyException이 발생한다")
    void update_ThrowsNoticeAdminOnlyException_WhenUserIsNotAdmin() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = false;
        Long noticeId = 1L;
        NoticeUpdateCommand command = createNoticeUpdateCommand();

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(userId);

        // when & then
        assertThrows(NoticeAdminOnlyException.class, () -> {
            noticeCommandService.update(userId, userIsAdmin, noticeId, command);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).updateNotice(any(), any());
    }

    @Test
    @DisplayName("관리자가 본인의 공지사항을 성공적으로 삭제한다")
    void delete_Success_WhenUserIsAdminAndAuthor() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(userId);

        // when
        noticeCommandService.delete(userId, userIsAdmin, noticeId);

        // then
        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper).deleteNotice(noticeId);
    }

    @Test
    @DisplayName("존재하지 않는 공지사항을 삭제하려 하면 NoticeNotFoundException이 발생한다")
    void delete_ThrowsNoticeNotFoundException_WhenNoticeNotFound() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(null);

        // when & then
        assertThrows(NoticeNotFoundException.class, () -> {
            noticeCommandService.delete(userId, userIsAdmin, noticeId);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).deleteNotice(any());
    }

    @Test
    @DisplayName("다른 사용자의 공지사항을 삭제하려 하면 NoticeSaveDeniedException이 발생한다")
    void delete_ThrowsNoticeSaveDeniedException_WhenUserIsNotAuthor() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = true;
        Long noticeId = 1L;
        Long differentAuthorId = 2L;

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(differentAuthorId);

        // when & then
        assertThrows(NoticeSaveDeniedException.class, () -> {
            noticeCommandService.delete(userId, userIsAdmin, noticeId);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).deleteNotice(any());
    }

    @Test
    @DisplayName("관리자가 아닌 사용자가 공지사항을 삭제하려 하면 NoticeAdminOnlyException이 발생한다")
    void delete_ThrowsNoticeAdminOnlyException_WhenUserIsNotAdmin() {
        // given
        Long userId = 1L;
        boolean userIsAdmin = false;
        Long noticeId = 1L;

        when(noticeMapper.getAuthorId(noticeId)).thenReturn(userId);

        // when & then
        assertThrows(NoticeAdminOnlyException.class, () -> {
            noticeCommandService.delete(userId, userIsAdmin, noticeId);
        });

        verify(noticeMapper).getAuthorId(noticeId);
        verify(noticeMapper, never()).deleteNotice(any());
    }

    private NoticeCreateCommand createNoticeCreateCommand() {
        return NoticeCreateCommand.builder()
                .title("테스트 공지사항 제목")
                .content("테스트 공지사항 내용")
                .authorId(1L)
                .isPinned(false)
                .build();
    }

    private NoticeUpdateCommand createNoticeUpdateCommand() {
        return NoticeUpdateCommand.builder()
                .title("업데이트된 공지사항 제목")
                .content("업데이트된 공지사항 내용")
                .authorId(1L)
                .isPinned(true)
                .build();
    }
}