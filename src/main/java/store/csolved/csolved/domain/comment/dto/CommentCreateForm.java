package store.csolved.csolved.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import store.csolved.csolved.domain.comment.Comment;

@Data
public class CommentCreateForm
{
    private Long answerId;

    private Long authorId;

    @NotNull
    private boolean anonymous;

    private String content;

    public Comment toComment()
    {
        return Comment.create(
                answerId,
                authorId,
                anonymous,
                content);
    }
}
