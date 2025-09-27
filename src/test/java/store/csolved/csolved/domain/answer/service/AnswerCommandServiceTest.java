package store.csolved.csolved.domain.answer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.record.AnswerDetailRecord;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;
import store.csolved.csolved.domain.post.mapper.PostMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerCommandServiceTest
{
    @Mock
    private AnswerMapper answerMapper;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private AnswerCommandService answerCommandService;

    @Test
    @DisplayName("답변을 성공적으로 저장한다")
    void save_Success()
    {
        // given
        Long userId = 1L;
        AnswerCreateCommand command = AnswerCreateCommand.builder()
                .postId(1L)
                .authorId(1L)
                .anonymous(false)
                .content("테스트 답변입니다.")
                .build();

        when(postMapper.isExist(1L)).thenReturn(true);

        // when
        assertDoesNotThrow(() -> answerCommandService.save(userId, command));

        // then
        verify(postMapper).isExist(1L);
        verify(answerMapper).increaseAnswerCount(1L);
        verify(answerMapper).saveAnswer(any(Answer.class));
    }

    @Test
    @DisplayName("존재하지 않는 게시글에 답변 작성시 예외가 발생한다")
    void save_PostNotFound()
    {
        // given
        Long userId = 1L;
        AnswerCreateCommand command = AnswerCreateCommand.builder()
                .postId(999L)
                .authorId(1L)
                .anonymous(false)
                .content("테스트 답변입니다.")
                .build();

        when(postMapper.isExist(999L)).thenReturn(false);

        // when & then
        CsolvedException exception = assertThrows(CsolvedException.class,
                () -> answerCommandService.save(userId, command));
        assertEquals(ExceptionCode.POST_NOT_FOUND, exception.getCode());

        verify(postMapper).isExist(999L);
        verify(answerMapper, never()).increaseAnswerCount(any());
        verify(answerMapper, never()).saveAnswer(any());
    }

    @Test
    @DisplayName("본인이 아닌 사용자 ID로 답변 작성시 예외가 발생한다")
    void save_SaveDenied()
    {
        // given
        Long userId = 1L;
        AnswerCreateCommand command = AnswerCreateCommand.builder()
                .postId(1L)
                .authorId(2L) // 다른 사용자 ID
                .anonymous(false)
                .content("테스트 답변입니다.")
                .build();

        when(postMapper.isExist(1L)).thenReturn(true);

        // when & then
        CsolvedException exception = assertThrows(CsolvedException.class,
                () -> answerCommandService.save(userId, command));
        assertEquals(ExceptionCode.ANSWER_SAVE_DENIED, exception.getCode());

        verify(postMapper).isExist(1L);
        verify(answerMapper, never()).increaseAnswerCount(any());
        verify(answerMapper, never()).saveAnswer(any());
    }

    @Test
    @DisplayName("댓글이 없는 답변을 성공적으로 삭제한다 (Hard Delete)")
    void delete_HardDelete_Success()
    {
        // given
        Long userId = 1L;
        Long answerId = 1L;

        AnswerDetailRecord answerRecord = AnswerDetailRecord.builder()
                .id(answerId)
                .postId(1L)
                .authorId(userId)
                .anonymous(false)
                .content("테스트 답변")
                .build();

        when(answerMapper.existComments(answerId)).thenReturn(false);
        when(answerMapper.getAnswer(answerId)).thenReturn(answerRecord);

        // when
        assertDoesNotThrow(() -> answerCommandService.delete(userId, answerId));

        // then
        verify(answerMapper).existComments(answerId);
        verify(answerMapper).getAnswer(answerId);
        verify(answerMapper).decreaseAnswerCount(1L);
        verify(answerMapper).hardDelete(answerId);
        verify(answerMapper, never()).softDelete(answerId);
    }

    @Test
    @DisplayName("댓글이 있는 답변을 성공적으로 삭제한다 (Soft Delete)")
    void delete_SoftDelete_Success()
    {
        // given
        Long userId = 1L;
        Long answerId = 1L;

        AnswerDetailRecord answerRecord = AnswerDetailRecord.builder()
                .id(answerId)
                .postId(1L)
                .authorId(userId)
                .anonymous(false)
                .content("테스트 답변")
                .build();

        when(answerMapper.existComments(answerId)).thenReturn(true);
        when(answerMapper.getAnswer(answerId)).thenReturn(answerRecord);

        // when
        assertDoesNotThrow(() -> answerCommandService.delete(userId, answerId));

        // then
        verify(answerMapper).existComments(answerId);
        verify(answerMapper).getAnswer(answerId);
        verify(answerMapper).softDelete(answerId);
        verify(answerMapper, never()).decreaseAnswerCount(any());
        verify(answerMapper, never()).hardDelete(answerId);
    }

    @Test
    @DisplayName("존재하지 않는 답변 삭제시 예외가 발생한다")
    void delete_AnswerNotFound()
    {
        // given
        Long userId = 1L;
        Long answerId = 999L;

        when(answerMapper.existComments(answerId)).thenReturn(false);
        when(answerMapper.getAnswer(answerId)).thenReturn(null);

        // when & then
        CsolvedException exception = assertThrows(CsolvedException.class,
                () -> answerCommandService.delete(userId, answerId));
        assertEquals(ExceptionCode.ANSWER_NOT_FOUND, exception.getCode());

        verify(answerMapper).existComments(answerId);
        verify(answerMapper).getAnswer(answerId);
        verify(answerMapper, never()).hardDelete(any());
        verify(answerMapper, never()).softDelete(any());
    }

    @Test
    @DisplayName("본인이 아닌 답변 삭제시 예외가 발생한다")
    void delete_DeleteDenied()
    {
        // given
        Long userId = 1L;
        Long answerId = 1L;

        AnswerDetailRecord answerRecord = AnswerDetailRecord.builder()
                .id(answerId)
                .postId(1L)
                .authorId(2L) // 다른 사용자 ID
                .anonymous(false)
                .content("테스트 답변")
                .build();

        when(answerMapper.existComments(answerId)).thenReturn(false);
        when(answerMapper.getAnswer(answerId)).thenReturn(answerRecord);

        // when & then
        CsolvedException exception = assertThrows(CsolvedException.class,
                () -> answerCommandService.delete(userId, answerId));
        assertEquals(ExceptionCode.ANSWER_DELETE_DENIED, exception.getCode());

        verify(answerMapper).existComments(answerId);
        verify(answerMapper).getAnswer(answerId);
        verify(answerMapper, never()).hardDelete(any());
        verify(answerMapper, never()).softDelete(any());
    }
}