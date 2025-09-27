package store.csolved.csolved.domain.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.comment.mapper.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void save(Comment comment);

    List<Comment> getComments(List<Long> answerIds);

    Comment getComment(Long commentId);

    void delete(Long commentId);
}
