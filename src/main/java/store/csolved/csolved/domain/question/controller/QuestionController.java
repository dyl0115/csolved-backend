package store.csolved.csolved.domain.question.controller;

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
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.common.page.Page;
import store.csolved.csolved.common.page.etc.PageInfo;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateUpdateViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionDetailViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.question.facade.QuestionFacade;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    public final static String VIEWS_QUESTION_CREATE_OR_UPDATE_FORM = "views/question/create";
    public final static String VIEWS_QUESTION_LIST = "views/domain/question/list";
    public final static String VIEWS_QUESTION_DETAIL = "views/question/detail";

    private final QuestionFacade questionFacade;

    @LoginRequest
    @GetMapping("/questions")
    public String getQuestions(@PageInfo Page page,
                               Model model)
    {
        QuestionListViewModel viewModel = questionFacade.getQuestions(page);
        model.addAttribute("questionListViewModel", viewModel);
        return VIEWS_QUESTION_LIST;
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}")
    public String getQuestion(@PathVariable Long questionId,
                              Model model)
    {
        QuestionDetailViewModel viewModel = questionFacade.getQuestion(questionId);
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        model.addAttribute("questionDetails", viewModel);
        return VIEWS_QUESTION_DETAIL;
    }

    @LoginRequest
    @GetMapping("/questions/create")
    public String getQuestionCreateForm(Model model)
    {
        QuestionCreateUpdateViewModel createViewModel = questionFacade.getQuestionCreateUpdateViewModel();
        model.addAttribute("questionViewModel", createViewModel);
        model.addAttribute("questionCreateForm", QuestionCreateUpdateForm.empty());
        return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
    }

    @LoginRequest
    @PostMapping("/questions/create")
    public String processCreateForm(@LoginUser User user,
                                    @Valid @ModelAttribute("questionCreateForm") QuestionCreateUpdateForm form,
                                    BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
        }
        else
        {
            questionFacade.saveQuestion(user.getId(), form);
            return "redirect:/questions?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}/edit-form")
    public String initUpdateForm(@PathVariable Long questionId,
                                 Model model)
    {
        QuestionCreateUpdateForm questionUpdateForm = questionFacade.getQuestionUpdateForm(questionId);
        model.addAttribute("questionEditForm", questionUpdateForm);
        return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/questions/{questionId}")
    public String processUpdateForm(@LoginUser User user,
                                    @PathVariable("questionId") Long questionId,
                                    @Valid @ModelAttribute("questionEditForm") QuestionCreateUpdateForm form,
                                    BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
        }

        questionFacade.updateQuestion(questionId, user.getId(), form);
        return "redirect:/questions?page=1";
    }

    @LoginRequest
    @DeleteMapping("/api/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId)
    {
        questionFacade.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @LoginRequest
    @PostMapping("/api/questions/{questionId}/likes")
    public ResponseEntity<Void> increaseLikes(@LoginUser User user,
                                              @PathVariable Long questionId)
    {
        boolean valid = questionFacade.increaseQuestionLikes(questionId, user.getId());

        if (!valid)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
