package store.csolved.csolved.domain.answer.service.result;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AnswerWithCommentsResult
{
    private Long id;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDetailRecord> comments;

    public static AnswerWithCommentsResult from(AnswerWithCommentsRecord record)
    {
        return AnswerWithCommentsResult.builder()
                .id(record.getId())
                .authorId(record.getAuthorId())
                .authorProfileImage(record.getAuthorProfileImage())
                .authorNickname(record.getAuthorNickname())
                .anonymous(record.isAnonymous())
                .content(record.getContent())
                .createdAt(record.getCreatedAt())
                .comments(record.getComments())
                .build();
    }

    public static List<AnswerWithCommentsResult> from(List<AnswerWithCommentsRecord> records)
    {
        return records.stream()
                .map(AnswerWithCommentsResult::from)
                .collect(Collectors.toList());
    }
}
