package store.csolved.csolved.domain.answer.mapper.record;

import lombok.*;
import store.csolved.csolved.common.BaseRecord;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AnswerDetailRecord extends BaseRecord
{
    private Long postId;
    private Long authorId;
    private boolean anonymous;
    private String content;
}
