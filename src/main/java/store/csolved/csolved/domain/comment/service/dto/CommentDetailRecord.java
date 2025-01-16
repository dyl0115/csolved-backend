package store.csolved.csolved.domain.comment.service.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class CommentDetailRecord
{
    private Long id;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
}
