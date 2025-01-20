package store.csolved.csolved.domain.comment.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class AnswerCommentMap
{
    private Long answerId;
    private List<Comment> comments;
}
