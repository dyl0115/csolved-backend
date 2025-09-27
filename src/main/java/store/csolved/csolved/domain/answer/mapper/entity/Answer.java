package store.csolved.csolved.domain.answer.mapper.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseEntity;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity
{
    private Long postId;
    private Long authorId;
    private Boolean anonymous;
    private String content;

    public static Answer from(AnswerCreateCommand command)
    {
        return Answer.builder()
                .postId(command.getPostId())
                .authorId(command.getAuthorId())
                .anonymous(command.getAnonymous())
                .content(command.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
