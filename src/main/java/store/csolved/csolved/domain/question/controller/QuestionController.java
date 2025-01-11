package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.annotation.LoginRequest;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.comment.dto.CommentCreateForm;
import store.csolved.csolved.domain.common.page.Page;
import store.csolved.csolved.domain.common.page.etc.PageInfo;
import store.csolved.csolved.domain.question.controller.dto.request.QuestionCreateForm;
import store.csolved.csolved.domain.question.controller.dto.QuestionDto;
import store.csolved.csolved.domain.question.controller.dto.QuestionEditForm;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class QuestionController
{
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryMapper categoryMapper;

    public final static String VIEWS_QUESTION_CREATE_OR_UPDATE_FORM = "questions/create";

    @ModelAttribute("categories")
    public List<Category> findAllCategory()
    {
        return categoryMapper.findAllCategory();
    }

    @LoginRequest
    @GetMapping("/questions")
    public String findQuestionList(@PageInfo Page page, Model model)
    {
        List<QuestionDto> questions = questionService.provideQuestions(page);
        model.addAttribute("questions", questions);
        return "questions/list";
    }

    @LoginRequest
    @GetMapping("/questions/create")
    public String initCreateForm(Model model)
    {
        model.addAttribute("questionCreateForm", new QuestionCreateForm());
        return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
    }

    @LoginRequest
    @PostMapping("/questions/create")
    public String processCreateForm(@Valid @ModelAttribute("questionCreateForm") QuestionCreateForm createForm,
                                    BindingResult result)
    {
        if (result.hasErrors())
        {
            return "questions/create";
        }
        else
        {
            questionService.saveQuestion(createForm);
            return "redirect:/questions?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}")
    public String findQuestion(@PathVariable Long questionId,
                               Model model)
    {
        questionService.increaseView(questionId);

        model.addAttribute("answerCreateForm", new AnswerCreateForm());
        model.addAttribute("commentCreateForm", new CommentCreateForm());
        model.addAttribute("question", questionService.provideQuestion(questionId));
        model.addAttribute("answers", answerService.provideAllAnswersByQuestionId(questionId));

        return "questions/detail";
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}/edit-form")
    public String initEditForm(@LoginUser User user,
                               @PathVariable Long questionId,
                               Model model)
    {
        // 내일 개선.!
        QuestionDto question = questionService.provideQuestion(questionId);
        QuestionEditForm questionEditForm = new QuestionEditForm(
                questionId,
                user.getId(),
                question.isAnonymous(),
                question.getCategoryId(),
                question.getTags().toString(),
                question.getTitle(),
                question.getContent());

        model.addAttribute("questionEditForm", questionEditForm);
        return "questions/edit";
    }

    @LoginRequest
    @PutMapping("/questions/{questionId}")
    public String processEditForm(@Valid @ModelAttribute("questionEditForm") QuestionEditForm questionEditForm,
                                  BindingResult result)
    {
        if (result.hasErrors()) return "questions/edit";

        questionService.updateQuestion(questionEditForm);
        return "redirect:/questions?page=1";
    }

    @LoginRequest
    @DeleteMapping("/api/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId)
    {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @LoginRequest
    @PostMapping("/api/questions/{questionId}/likes")
    public ResponseEntity<Void> increaseLikes(@LoginUser User user,
                                              @PathVariable Long questionId)
    {
        if (questionService.hasAlreadyLiked(questionId, user.getId()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).body(null);
        }

        questionService.increaseLike(questionId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
