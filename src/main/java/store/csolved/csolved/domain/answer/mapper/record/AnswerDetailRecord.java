package store.csolved.csolved.domain.answer.mapper.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseRecord;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AnswerDetailRecord extends BaseRecord
{
    private Long postId;
    private Long authorId;
    private Boolean anonymous;
    private String content;
}
