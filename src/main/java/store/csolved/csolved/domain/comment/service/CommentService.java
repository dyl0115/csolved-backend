package store.csolved.csolved.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.mapper.CommentMapper;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Service
public class CommentService
{
    private final CommentMapper commentMapper;

    @Transactional
    public void saveComment(User user, CommentCreateForm form)
    {
        form.setAuthorId(user.getId());
        commentMapper.insertComment(form.toComment());
    }

    @Transactional
    public void deleteComment(Long commentId)
    {
        commentMapper.deleteCommentByCommentId(commentId);
    }
}
