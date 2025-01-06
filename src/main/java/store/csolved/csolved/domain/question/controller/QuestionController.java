package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import store.csolved.csolved.domain.question.dto.QuestionDto;
import store.csolved.csolved.domain.question.dto.QuestionEditForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/questions")
    public String provideQuestions(@LoginUser User user,
                                   @ModelAttribute("page") Page page,
                                   Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("questions", questionService.provideQuestions(page));

        return "article/list";
    }

    @GetMapping("/questions/create")
    public String provideQuestionForm(@LoginUser User user,
                                      Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("questionCreateForm", new QuestionCreateForm());
        model.addAttribute("categories", categoryMapper.findAllCategory());

        return "article/create";
    }

    @PostMapping("/questions/create")
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

            return "article/create";
        }
        else
        {
            questionService.saveQuestion(createForm);

            return "redirect:/questions";
        }
    }

    @GetMapping("/questions/{questionId}")
    public String provideQuestionDetail(@LoginUser User user,
                                        @PathVariable Long questionId,
                                        Model model)
    {
        questionService.increaseView(questionId);

        model.addAttribute("user", user);
        model.addAttribute("answerCreateForm", new AnswerCreateForm());
        model.addAttribute("commentCreateForm", new CommentCreateForm());
        model.addAttribute("question", questionService.provideQuestion(questionId));
        model.addAttribute("answers", answerService.provideAllAnswersByQuestionId(questionId));

        return "article/detail";
    }

    @GetMapping("/questions/{questionId}/edit-form")
    public String provideQuestionEditForm(@LoginUser User user,
                                          @PathVariable Long questionId,
                                          Model model)
    {
        QuestionDto question = questionService.provideQuestion(questionId);
        QuestionEditForm questionEditForm = new QuestionEditForm(
                questionId,
                user.getId(),
                question.isAnonymous(),
                question.getCategoryId(),
                question.getTags().toString(),
                question.getTitle(),
                question.getContent());

        model.addAttribute("user", user);
        model.addAttribute("questionEditForm", questionEditForm);
        model.addAttribute("categories", categoryMapper.findAllCategory());

        return "article/edit";
    }

    @PutMapping("/questions/{questionId}")
    public String updateQuestion(@LoginUser User user,
                                 @ModelAttribute("questionEditForm") QuestionEditForm questionEditForm,
                                 BindingResult result)
    {
        if (result.hasErrors())
        {
            return "article/edit";
        }
        else
        {
            questionService.updateQuestion(questionEditForm);
            return "redirect:/questions";
        }
    }

    @DeleteMapping("/api/questions/{questionId}")
    @ResponseBody
    public void deleteQuestion(@LoginUser User user,
                               @PathVariable Long questionId)
    {
        questionService.deleteQuestion(questionId);
    }

    @PostMapping("/api/questions/{questionId}/likes")
    public ResponseEntity<Void> increaseLikes(@LoginUser User user,
                                              @PathVariable Long questionId)
    {
        if (questionService.hasAlreadyLiked(questionId, user.getId()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        questionService.increaseLike(questionId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
