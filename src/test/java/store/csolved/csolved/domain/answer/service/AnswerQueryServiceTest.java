package store.csolved.csolved.domain.answer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerQueryServiceTest
{
    @Mock
    private AnswerMapper answerMapper;

    @InjectMocks
    private AnswerQueryService answerQueryService;

    @Test
    @DisplayName("게시글의 답변과 댓글을 모두 조회한다")
    void getAnswersWithComments_Success()
    {
        // given
        Long postId = 1L;
        LocalDateTime now = LocalDateTime.now();

        CommentDetailRecord comment1 = CommentDetailRecord.builder()
                .id(1L)
                .answerId(1L)
                .authorId(2L)
                .authorProfileImage("profile2.jpg")
                .authorNickname("commenter1")
                .anonymous(false)
                .content("첫 번째 댓글")
                .createdAt(now.minusMinutes(10))
                .build();

        CommentDetailRecord comment2 = CommentDetailRecord.builder()
                .id(2L)
                .answerId(1L)
                .authorId(3L)
                .authorProfileImage("profile3.jpg")
                .authorNickname("commenter2")
                .anonymous(true)
                .content("두 번째 댓글")
                .createdAt(now.minusMinutes(5))
                .build();

        AnswerWithCommentsRecord answer1 = AnswerWithCommentsRecord.builder()
                .id(1L)
                .authorId(1L)
                .authorProfileImage("profile1.jpg")
                .authorNickname("answerer1")
                .anonymous(false)
                .content("첫 번째 답변입니다.")
                .createdAt(now.minusMinutes(30))
                .comments(Arrays.asList(comment1, comment2))
                .build();

        AnswerWithCommentsRecord answer2 = AnswerWithCommentsRecord.builder()
                .id(2L)
                .authorId(4L)
                .authorProfileImage("profile4.jpg")
                .authorNickname("answerer2")
                .anonymous(true)
                .content("두 번째 답변입니다.")
                .createdAt(now.minusMinutes(20))
                .comments(Collections.emptyList())
                .build();

        List<AnswerWithCommentsRecord> mockRecords = Arrays.asList(answer1, answer2);

        when(answerMapper.getAnswersWithComments(postId)).thenReturn(mockRecords);

        // when
        List<AnswerWithCommentsResult> results = answerQueryService.getAnswersWithComments(postId);

        // then
        verify(answerMapper).getAnswersWithComments(postId);

        assertThat(results).hasSize(2);

        // 첫 번째 답변 검증
        AnswerWithCommentsResult result1 = results.get(0);
        assertThat(result1.getId()).isEqualTo(1L);
        assertThat(result1.getAuthorId()).isEqualTo(1L);
        assertThat(result1.getAuthorProfileImage()).isEqualTo("profile1.jpg");
        assertThat(result1.getAuthorNickname()).isEqualTo("answerer1");
        assertThat(result1.getAnonymous()).isFalse();
        assertThat(result1.getContent()).isEqualTo("첫 번째 답변입니다.");
        assertThat(result1.getCreatedAt()).isEqualTo(now.minusMinutes(30));
        assertThat(result1.getComments()).hasSize(2);

        // 두 번째 답변 검증
        AnswerWithCommentsResult result2 = results.get(1);
        assertThat(result2.getId()).isEqualTo(2L);
        assertThat(result2.getAuthorId()).isEqualTo(4L);
        assertThat(result2.getAuthorProfileImage()).isEqualTo("profile4.jpg");
        assertThat(result2.getAuthorNickname()).isEqualTo("answerer2");
        assertThat(result2.getAnonymous()).isTrue();
        assertThat(result2.getContent()).isEqualTo("두 번째 답변입니다.");
        assertThat(result2.getCreatedAt()).isEqualTo(now.minusMinutes(20));
        assertThat(result2.getComments()).isEmpty();
    }

    @Test
    @DisplayName("답변이 없는 게시글의 경우 빈 리스트를 반환한다")
    void getAnswersWithComments_EmptyList()
    {
        // given
        Long postId = 1L;
        when(answerMapper.getAnswersWithComments(postId)).thenReturn(Collections.emptyList());

        // when
        List<AnswerWithCommentsResult> results = answerQueryService.getAnswersWithComments(postId);

        // then
        verify(answerMapper).getAnswersWithComments(postId);
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("댓글이 없는 답변만 있는 경우를 처리한다")
    void getAnswersWithComments_AnswersWithoutComments()
    {
        // given
        Long postId = 1L;
        LocalDateTime now = LocalDateTime.now();

        AnswerWithCommentsRecord answer = AnswerWithCommentsRecord.builder()
                .id(1L)
                .authorId(1L)
                .authorProfileImage("profile1.jpg")
                .authorNickname("answerer1")
                .anonymous(false)
                .content("댓글이 없는 답변입니다.")
                .createdAt(now)
                .comments(Collections.emptyList())
                .build();

        when(answerMapper.getAnswersWithComments(postId)).thenReturn(Collections.singletonList(answer));

        // when
        List<AnswerWithCommentsResult> results = answerQueryService.getAnswersWithComments(postId);

        // then
        verify(answerMapper).getAnswersWithComments(postId);

        assertThat(results).hasSize(1);
        AnswerWithCommentsResult result = results.get(0);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("댓글이 없는 답변입니다.");
        assertThat(result.getComments()).isEmpty();
    }

    @Test
    @DisplayName("익명 답변과 익명 댓글을 올바르게 처리한다")
    void getAnswersWithComments_AnonymousContent()
    {
        // given
        Long postId = 1L;
        LocalDateTime now = LocalDateTime.now();

        CommentDetailRecord anonymousComment = CommentDetailRecord.builder()
                .id(1L)
                .answerId(1L)
                .authorId(2L)
                .authorProfileImage(null) // 익명일 때는 프로필 이미지 없음
                .authorNickname("익명")
                .anonymous(true)
                .content("익명 댓글입니다.")
                .createdAt(now)
                .build();

        AnswerWithCommentsRecord anonymousAnswer = AnswerWithCommentsRecord.builder()
                .id(1L)
                .authorId(1L)
                .authorProfileImage(null) // 익명일 때는 프로필 이미지 없음
                .authorNickname("익명")
                .anonymous(true)
                .content("익명 답변입니다.")
                .createdAt(now)
                .comments(Collections.singletonList(anonymousComment))
                .build();

        when(answerMapper.getAnswersWithComments(postId)).thenReturn(Collections.singletonList(anonymousAnswer));

        // when
        List<AnswerWithCommentsResult> results = answerQueryService.getAnswersWithComments(postId);

        // then
        verify(answerMapper).getAnswersWithComments(postId);

        assertThat(results).hasSize(1);
        AnswerWithCommentsResult result = results.get(0);
        assertThat(result.getAnonymous()).isTrue();
        assertThat(result.getAuthorNickname()).isEqualTo("익명");
        assertThat(result.getAuthorProfileImage()).isNull();
        assertThat(result.getComments()).hasSize(1);
        assertThat(result.getComments().get(0).getAnonymous()).isTrue();
    }
}