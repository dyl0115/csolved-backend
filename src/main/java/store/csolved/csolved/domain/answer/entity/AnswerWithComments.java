package store.csolved.csolved.domain.answer.entity;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.entity.AnswerCommentMap;

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
    private Long total_score;
    private Long voter_count;
    private LocalDateTime createdAt;
    private List<Comment> comments;

    public static AnswerWithComments from(Answer answer,
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

    public static List<AnswerWithComments> from(List<Answer> answers,
                                                Map<Long, AnswerCommentMap> commentMap)
    {
        return answers.stream()
                .map(
                        answer ->
                        {
                            Long answerId = answer.getId();
                            AnswerCommentMap answerComments = commentMap.getOrDefault(answerId, null);
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