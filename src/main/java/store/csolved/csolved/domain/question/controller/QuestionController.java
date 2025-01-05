package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@RequestMapping("/questions")
@Controller
public class QuestionController
{
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public String provideQuestions(@LoginUser User user,
                                   @ModelAttribute("page") Page page,
                                   Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("questions", questionService.provideQuestions(page));

        return "article/list";
    }

    @GetMapping("/create")
    public String provideQuestionForm(@LoginUser User user,
                                      Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("questionCreateForm", new QuestionCreateForm());
        model.addAttribute("categories", categoryMapper.findAllCategory());

        return "questions/questions-create";
    }

    @PostMapping("/create")
    public String saveQuestion(@LoginUser User user,
                               @Valid @ModelAttribute("questionCreateForm") QuestionCreateForm createForm,
                               BindingResult result,
                               Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("user", user);
            model.addAttribute("questionCreateForm", createForm);
            model.addAttribute("categories", categoryMapper.findAllCategory());

            return "questions/questions-create";
        }
        else
        {
            questionService.saveQuestion(createForm);

            return "redirect:/questions";
        }
    }

    @GetMapping("/{questionId}")
    public String provideQuestionDetail(@LoginUser User user,
                                        @PathVariable Long questionId,
                                        Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("answerCreateForm", new AnswerCreateForm());
        model.addAttribute("commentCreateForm", new CommentCreateForm());
        model.addAttribute("question", questionService.provideQuestion(questionId));
        model.addAttribute("answers", answerService.provideAllAnswersByQuestionId(questionId));

        return "article/detail";
    }
}
