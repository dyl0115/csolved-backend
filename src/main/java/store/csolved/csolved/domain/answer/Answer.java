package store.csolved.csolved.domain.answer;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity
{
    private Long postId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
}
