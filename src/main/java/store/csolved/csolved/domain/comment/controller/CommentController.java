package store.csolved.csolved.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
