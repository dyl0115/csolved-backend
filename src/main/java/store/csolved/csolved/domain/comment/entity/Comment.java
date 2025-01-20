package store.csolved.csolved.domain.comment.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Comment
{
    private Long id;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
}
