package store.csolved.csolved.domain.answer.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;
import store.csolved.csolved.domain.comment.controller.response.CommentDetailResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AnswerWithCommentsResponse
{
    private Long id;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private Boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDetailResponse> comments;

    public static AnswerWithCommentsResponse from(AnswerWithCommentsResult response)
    {
        return AnswerWithCommentsResponse.builder()
                .id(response.getId())
                .authorId(response.getAuthorId())
                .authorProfileImage(response.getAuthorProfileImage())
                .authorNickname(response.getAuthorNickname())
                .anonymous(response.getAnonymous())
                .content(response.getContent())
                .createdAt(response.getCreatedAt())
                .comments(CommentDetailResponse.from(response.getComments()))
                .build();
    }

    public static List<AnswerWithCommentsResponse> from(List<AnswerWithCommentsResult> results)
    {
        return results.stream()
                .map(AnswerWithCommentsResponse::from)
                .collect(Collectors.toList());
    }
}
