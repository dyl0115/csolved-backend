package store.csolved.csolved.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto
{
    private Long commentId;

    private Long answerId;

    private Long authorId;

    private boolean isAnonymous;

    private String authorNickname;

    private String content;

    private LocalDateTime createdAt;
}
