package store.csolved.csolved.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class CommentController
{
    private final CommentService commentService;

    @PostMapping("/questions/{questionId}/answers/{answerId}/comment")
    public String saveComment(@LoginUser User user,
                              @PathVariable("questionId") Long questionId,
                              @ModelAttribute("commentCreateForm") CommentCreateForm commentCreateForm)
    {
        commentService.saveComment(user, commentCreateForm);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/api/comments/{commentId}")
    @ResponseBody
    public String deleteComment(@LoginUser User user,
                                @PathVariable Long commentId)
    {
        commentService.deleteComment(commentId);

        return "ok";
    }
}
