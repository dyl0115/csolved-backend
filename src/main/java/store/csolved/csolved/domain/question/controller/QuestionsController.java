package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.question.dto.QuestionFormResponse;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@RequestMapping("/questions")
@Controller
public class QuestionsController
{
    private final QuestionService questionService;

    @GetMapping("")
    public String questions(@LoginUser User user)
    {
        return "questions_loby";
    }

    @GetMapping("/create")
    public String provideQuestionForm(@LoginUser User user,
                                      @ModelAttribute("questionForm") QuestionFormResponse questionForm)
    {
        questionService.provideQuestionForm(user, questionForm);
        return "/questions/questionsForm";
    }
}
