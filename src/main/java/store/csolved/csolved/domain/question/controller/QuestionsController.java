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
import store.csolved.csolved.domain.question.dto.QuestionFormRequest;
import store.csolved.csolved.domain.question.dto.QuestionCreateDto;
import store.csolved.csolved.domain.question.dto.QuestionListDto;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import static store.csolved.csolved.domain.question.Page.*;

@RequiredArgsConstructor
@RequestMapping("/questions")
@Controller
public class QuestionsController
{
    private final QuestionService questionService;

    @GetMapping("")
    public String questions(@LoginUser User user,
                            @ModelAttribute("questionList") QuestionListDto questionList)
    {
        questionService.provideQuestions(user,
                questionList,
                new Page(DEFAULT_OFFSET, DEFAULT_LIMIT));
        return "/questions/questions-home";
    }

    @GetMapping("/create")
    public String provideQuestionForm(@LoginUser User user,
                                      @ModelAttribute("questionCreateForm") QuestionCreateDto questionCreateDto)
    {
        questionService.provideQuestionForm(user, questionCreateDto);
        return "/questions/questions-create";
    }

    @PostMapping("/create")
    public String saveQuestion(@LoginUser User user,
                               @Valid @ModelAttribute("questionCreateForm") QuestionFormRequest questionForm,
                               BindingResult questionFormErrors)
    {
        if (questionFormErrors.hasErrors())
        {
            return "/questions/questions-create";
        }
        else
        {
            questionService.saveQuestions(user, questionForm);
            return "redirect:/questions";
        }
    }

}
