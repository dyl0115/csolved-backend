package store.csolved.csolved.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.question.controller.QuestionController;
import store.csolved.csolved.domain.question.facade.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class CommentController
{
    private final CommentService commentService;
    private final QuestionFacade questionFacade;

    @LoginRequest
    @PostMapping("/comments")
    public String save(@RequestParam("postId") Long questionId,
                       @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                       BindingResult result,
                       Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.getQuestion(questionId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            model.addAttribute("commentCreateFrom", form);
            return QuestionController.VIEWS_QUESTION_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/questions/" + questionId;
    }
}
