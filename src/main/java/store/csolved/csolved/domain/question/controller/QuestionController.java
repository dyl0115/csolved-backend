package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.dto.AnswerListForm;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.dto.QuestionDetailForm;
import store.csolved.csolved.domain.question.dto.QuestionListForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import java.util.List;

import static store.csolved.csolved.domain.question.Page.*;

@RequiredArgsConstructor
@RequestMapping("/questions")
@Controller
public class QuestionController
{
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("")
    public String provideQuestions(@LoginUser @ModelAttribute("user") User user,
                                   @ModelAttribute("questionListForm") QuestionListForm questionList)
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
                               @Valid @ModelAttribute("questionCreateForm") QuestionCreateForm createForm,
                               BindingResult result)
    {
        if (result.hasErrors())
        {
            questionService.provideQuestionForm(user, createForm);
            return "/questions/questions-create";
        }
        else
        {
            questionService.saveQuestion(user, createForm);
            return "redirect:/questions";
        }
    }

    @GetMapping("/{questionId}")
    public String provideQuestionDetail(@LoginUser @ModelAttribute("user") User user,
                                        @PathVariable Long questionId,
                                        @ModelAttribute("questionDetailForm") QuestionDetailForm questionDetailForm,
                                        @ModelAttribute("answerCreateForm") AnswerCreateForm answerCreateForm,
                                        @ModelAttribute("answerListForm") AnswerListForm answerListForm,
                                        @ModelAttribute("commentCreateForm") CommentCreateForm commentCreateForm)
    {
        questionService.provideQuestion(user, questionId, questionDetailForm);
        return "/questions/questions-detail";
    }
}
