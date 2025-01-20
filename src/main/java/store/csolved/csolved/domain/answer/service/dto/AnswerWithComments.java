package store.csolved.csolved.domain.answer.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.service.dto.record.AnswerDetailRecord;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.entity.AnswerComments;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class AnswerWithComments
{
    private Long id;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
    private List<Comment> comments;

    public static AnswerWithComments from(AnswerDetailRecord answer,
                                          List<Comment> comments)
    {
        return AnswerWithComments.builder()
                .id(answer.getId())
                .authorId(answer.getAuthorId())
                .authorNickname(answer.getAuthorNickname())
                .anonymous(answer.isAnonymous())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .comments(comments)
                .build();
    }

    public static List<AnswerWithComments> from(List<AnswerDetailRecord> answers,
                                                Map<Long, AnswerComments> commentMap)
    {
        return answers.stream()
                .map(
                        answer ->
                        {
                            Long answerId = answer.getId();
                            AnswerComments answerComments = commentMap.getOrDefault(answerId, null);
                            if (answerComments != null)
                            {
                                List<Comment> comments = commentMap.get(answerId).getComments();
                                return AnswerWithComments.from(answer, comments);
                            }
                            return AnswerWithComments.from(answer, Collections.emptyList());
                        }
                ).toList();
    }
}