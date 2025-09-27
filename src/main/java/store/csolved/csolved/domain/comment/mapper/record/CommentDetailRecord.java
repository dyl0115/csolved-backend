package store.csolved.csolved.domain.comment.mapper.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseRecord;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CommentDetailRecord extends BaseRecord
{
    private Long answerId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
}

