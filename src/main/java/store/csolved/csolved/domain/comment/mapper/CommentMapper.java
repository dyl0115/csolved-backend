package store.csolved.csolved.domain.comment.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailListRecord;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailRecord;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper
{
    void insertComment(Comment comment);

    @MapKey("answerId")
    Map<Long, CommentDetailListRecord> getCommentsByAnswerIds(List<Long> answerIds);

    void deleteCommentByCommentId(Long commentId);
}
