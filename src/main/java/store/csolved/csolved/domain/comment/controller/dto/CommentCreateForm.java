package store.csolved.csolved.domain.comment.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.comment.Comment;

@Builder
@Data
public class CommentCreateForm
{
    private Long answerId;

    private Long authorId;

    @NotNull
    private boolean anonymous;

    private String content;

    public static CommentCreateForm empty()
    {
        return CommentCreateForm.builder().build();
    }

    public Comment toComment()
    {
        return Comment.create(
                answerId,
                authorId,
                anonymous,
                content);
    }
}
