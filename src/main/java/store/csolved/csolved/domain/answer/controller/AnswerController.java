package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.question.controller.QuestionController;
import store.csolved.csolved.domain.question.facade.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class AnswerController
{
    private final QuestionFacade questionFacade;
    private final AnswerService answerService;

    @LoginRequest
    @PostMapping("/questions/{questionId}/answers")
    public String saveAnswer(@PathVariable Long questionId,
                             @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                             BindingResult result,
                             Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.getQuestion(questionId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return QuestionController.VIEWS_QUESTION_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/questions/" + questionId;
    }
}