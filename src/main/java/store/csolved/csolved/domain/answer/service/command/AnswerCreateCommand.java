package store.csolved.csolved.domain.answer.service.command;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;

import java.time.LocalDateTime;

@Data
@Builder
public class AnswerCreateCommand
{
    private Long postId;
    private Long authorId;
    private boolean anonymous;
    private String content;

    public static AnswerCreateCommand from(AnswerCreateRequest request)
    {
        return AnswerCreateCommand.builder()
                .postId(request.getPostId())
                .authorId(request.getAuthorId())
                .anonymous(request.getAnonymous())
                .content(request.getContent())
                .build();
    }
}
