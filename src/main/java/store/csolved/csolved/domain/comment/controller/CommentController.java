package store.csolved.csolved.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.comment.controller.request.CommentCreateRequest;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.utils.login.LoginUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController
{
    private final CommentService commentService;

    //    @LoginRequest
    @PostMapping("/community/{postId}/answer/{answerId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCommunityComment(@Valid @RequestBody CommentCreateRequest request)
    {
        commentService.saveComment(request.toCommand());
    }

    //    @LoginRequest
    @DeleteMapping("/community/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommunityComment(@LoginUser User user,
                                       @PathVariable("commentId") Long commentId)
    {
        commentService.delete(user.getId(), commentId);
    }
}
