package store.csolved.csolved.domain.answer.mapper.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseRecord;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AnswerWithCommentsRecord extends BaseRecord
{
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private List<CommentDetailRecord> comments;
}