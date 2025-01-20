package store.csolved.csolved.domain.comment.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.entity.AnswerComments;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper
{
    void save(Comment comment);

    @MapKey("answerId")
    Map<Long, AnswerComments> getComments(List<Long> answerIds);

    void delete(Long commentId);
}
