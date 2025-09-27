package store.csolved.csolved.domain.comment.service.result;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommentDetailResult
{
    private Long answerId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private Boolean anonymous;
    private String content;

    public static CommentDetailResult from(CommentDetailRecord record)
    {
        return CommentDetailResult.builder()
                .answerId(record.getAnswerId())
                .authorId(record.getAuthorId())
                .authorProfileImage(record.getAuthorProfileImage())
                .authorNickname(record.getAuthorNickname())
                .anonymous(record.isAnonymous())
                .content(record.getContent())
                .build();
    }

    public static List<CommentDetailResult> from(List<CommentDetailRecord> records)
    {
        return records.stream()
                .map(CommentDetailResult::from)
                .collect(Collectors.toList());
    }
}
