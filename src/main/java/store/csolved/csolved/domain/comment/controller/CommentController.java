package store.csolved.csolved.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.annotation.LoginRequest;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class CommentController
{
    private final CommentService commentService;

    @LoginRequest
    @PostMapping("/questions/{questionId}/answers/{answerId}/comment")
    public String saveComment(@LoginUser User user,
                              @PathVariable("questionId") Long questionId,
                              @ModelAttribute("commentCreateForm") CommentCreateForm commentCreateForm)
    {
        commentService.saveComment(user, commentCreateForm);

        return "redirect:/questions/" + questionId;
    }

    @LoginRequest
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId)
    {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }
}
