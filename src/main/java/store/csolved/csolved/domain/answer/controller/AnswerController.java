package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.question.facade.QuestionFacade;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AnswerController
{
    public final static String VIEWS_QUESTION_DETAIL = "views/domain/question/detail";

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
            return VIEWS_QUESTION_DETAIL;
        }
        answerService.save(form.toAnswer());
        return "redirect:/questions/" + questionId;
    }
}