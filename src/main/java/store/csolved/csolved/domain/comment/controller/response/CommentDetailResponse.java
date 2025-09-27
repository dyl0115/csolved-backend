package store.csolved.csolved.domain.comment.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;
import store.csolved.csolved.domain.comment.service.result.CommentDetailResult;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommentDetailResponse
{
    private Long answerId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private Boolean anonymous;
    private String content;

    public static CommentDetailResponse from(CommentDetailResult result)
    {
        return CommentDetailResponse.builder()
                .answerId(result.getAnswerId())
                .authorId(result.getAuthorId())
                .authorProfileImage(result.getAuthorProfileImage())
                .authorNickname(result.getAuthorNickname())
                .anonymous(result.getAnonymous())
                .content(result.getContent())
                .build();
    }

    public static List<CommentDetailResponse> from(List<CommentDetailResult> results)
    {
        return results.stream()
                .map(CommentDetailResponse::from)
                .collect(Collectors.toList());
    }
}
