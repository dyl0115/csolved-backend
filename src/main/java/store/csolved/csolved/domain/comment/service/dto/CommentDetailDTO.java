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
    private boolean anonymous;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDetailDTO from(CommentDetailRecord record)
    {
        return CommentDetailDTO.builder()
                .id(record.getId())
                .anonymous(record.isAnonymous())
                .authorNickname(record.getAuthorNickname())
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
