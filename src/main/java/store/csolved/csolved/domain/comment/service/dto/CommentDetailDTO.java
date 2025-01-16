package store.csolved.csolved.domain.comment.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentDetailDTO
{
    private Long id;
    private Long answerId;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDetailDTO from(CommentDetailRecord record)
    {
        return CommentDetailDTO.builder()
                .id(record.getId())
                .authorId(record.getAuthorId())
                .authorNickname(record.getAuthorNickname())
                .anonymous(record.isAnonymous())
                .content(record.getContent())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public static List<CommentDetailDTO> from(List<CommentDetailRecord> records)
    {
        return records.stream()
                .map(CommentDetailDTO::from)
                .toList();
    }
}
