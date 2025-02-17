package store.csolved.csolved.domain.comment;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity
{
    private Long postId;
    private Long answerId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
}
