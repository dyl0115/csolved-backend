package store.csolved.csolved.domain.answer.mapper.record;

import lombok.*;
import store.csolved.csolved.common.BaseRecord;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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