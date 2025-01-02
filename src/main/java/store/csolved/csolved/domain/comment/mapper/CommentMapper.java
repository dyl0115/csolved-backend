package store.csolved.csolved.domain.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.comment.dto.CommentDto;

import java.util.List;

@Mapper
public interface CommentMapper
{
    void insertComment(Comment comment);

    List<CommentDto> findAllCommentsByAnswerId(Long answerId);
}
