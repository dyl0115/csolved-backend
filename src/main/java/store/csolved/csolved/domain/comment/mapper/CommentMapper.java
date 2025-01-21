package store.csolved.csolved.domain.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.comment.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void save(Comment comment);

    List<Comment> getComments(List<Long> answerIds);

    void delete(Long commentId);
}
