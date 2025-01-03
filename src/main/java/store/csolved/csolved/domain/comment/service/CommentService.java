package store.csolved.csolved.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.mapper.CommentMapper;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Service
public class CommentService
{
    private final CommentMapper commentMapper;

    public void saveComment(User user, CommentCreateForm form)
    {
        form.setAuthorId(user.getId());
        commentMapper.insertComment(form.toComment());
    }
}
