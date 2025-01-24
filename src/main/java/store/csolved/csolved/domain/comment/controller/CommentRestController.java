package store.csolved.csolved.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.user.User;

import java.util.Objects;

@RequestMapping("api/comments")
@RequiredArgsConstructor
@RestController
public class CommentRestController
{
    private final CommentService commentService;

    @LoginRequest
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@LoginUser User user,
                       @PathVariable("commentId") Long commentId)
    {
        commentService.delete(user.getId(), commentId);
    }
}
