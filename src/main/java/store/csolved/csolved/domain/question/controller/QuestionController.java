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
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.dto.QuestionSaveForm;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.dto.QuestionListForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import static store.csolved.csolved.domain.question.Page.*;

@RequiredArgsConstructor
@RequestMapping("/questions")
@Controller
public class QuestionController
{
    private final QuestionService questionService;

    @GetMapping("")
    public String provideQuestions(@LoginUser @ModelAttribute("user") User user,
                                   @ModelAttribute("questionList") QuestionListForm questionList)
    {
        questionService.provideQuestions(user,
                questionList,
                new Page(DEFAULT_OFFSET, DEFAULT_LIMIT));
        return "/questions/questions-home";
    }

    @GetMapping("/create")
    public String provideQuestionForm(@LoginUser User user,
                                      @ModelAttribute("questionCreateForm") QuestionCreateForm createForm)
    {
        questionService.provideQuestionForm(user, createForm);
        return "/questions/questions-create";
    }

    @PostMapping("/create")
    public String saveQuestion(@LoginUser User user,
                               @Valid @ModelAttribute("questionSaveForm") QuestionSaveForm saveForm,
                               BindingResult questionSaveErrors)
    {
        if (questionSaveErrors.hasErrors())
        {
            return "/questions/questions-create";
        }
        else
        {
            questionService.saveQuestion(user, saveForm);
            return "redirect:/questions";
        }
    }
}
