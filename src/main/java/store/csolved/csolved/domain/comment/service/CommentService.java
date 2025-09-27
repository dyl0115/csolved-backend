package store.csolved.csolved.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.comment.mapper.entity.Comment;
import store.csolved.csolved.domain.comment.mapper.CommentMapper;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService
{
    private final CommentMapper commentMapper;

    @Transactional
    public void saveComment(Comment comment)
    {
        commentMapper.save(comment);
    }

    public Map<Long, List<Comment>> getComments(List<Long> answerIds)
    {
        List<Comment> comments = commentMapper.getComments(answerIds);
        return comments.stream()
                .collect(Collectors.groupingBy(Comment::getAnswerId));
    }

    @Transactional
    public void delete(Long userId, Long commentId)
    {
        Comment comment = commentMapper.getComment(commentId);
        if (!Objects.equals(comment.getAuthorId(), userId))
        {
            throw new CsolvedException(ExceptionCode.ACCESS_DENIED);
        }
        commentMapper.delete(commentId);
    }
}
