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
import store.csolved.csolved.common.filter.Filtering;
import store.csolved.csolved.common.search.SearchInfo;
import store.csolved.csolved.common.search.Searching;
import store.csolved.csolved.common.sort.Sorting;
import store.csolved.csolved.common.filter.FilterInfo;
import store.csolved.csolved.common.sort.SortInfo;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.common.page.PageInfo;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionDetailVM;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.question.facade.QuestionFacade;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    public final static String VIEWS_QUESTION_CREATE_OR_UPDATE_FORM = "views/domain/question/create";
    public final static String VIEWS_QUESTION_LIST = "views/domain/question/list";
    public final static String VIEWS_QUESTION_DETAIL = "views/domain/question/detail";

    private final QuestionFacade questionFacade;

    @LoginRequest
    @GetMapping("/questions")
    public String getQuestions(@PageInfo Long page,
                               @SortInfo Sorting sort,
                               @FilterInfo Filtering filter,
                               @SearchInfo Searching search,
                               Model model)
    {
        QuestionListViewModel viewModel = questionFacade.getQuestions(page, sort, filter, search);
        model.addAttribute("questionListViewModel", viewModel);
        return VIEWS_QUESTION_LIST;
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}")
    public String getQuestion(@PathVariable Long questionId,
                              Model model)
    {
        QuestionDetailVM questionVM = questionFacade.getQuestion(questionId);
        model.addAttribute("questionDetails", questionVM);
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return VIEWS_QUESTION_DETAIL;
    }

    @LoginRequest
    @GetMapping("/questions/create")
    public String initSave(Model model)
    {
        QuestionCreateUpdateVM viewModel = questionFacade.initCreate();
        model.addAttribute("questionCreateVM", viewModel);
        model.addAttribute("createForm", QuestionCreateUpdateForm.empty());
        return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
    }

    @LoginRequest
    @PostMapping("/questions/create")
    public String processSave(@Valid @ModelAttribute("createForm") QuestionCreateUpdateForm form,
                              BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
        }
        else
        {
            questionFacade.save(form);
            return "redirect:/questions?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}/edit-form")
    public String initUpdate(@PathVariable Long questionId,
                             Model model)
    {
        QuestionCreateUpdateForm questionUpdateForm = questionFacade.initUpdate(questionId);
        model.addAttribute("questionEditForm", questionUpdateForm);
        return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/questions/{questionId}")
    public String processUpdate(@PathVariable("questionId") Long questionId,
                                @Valid @ModelAttribute("questionEditForm") QuestionCreateUpdateForm form,
                                BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_QUESTION_CREATE_OR_UPDATE_FORM;
        }

        questionFacade.update(questionId, form);
        return "redirect:/questions?page=1";
    }

    @LoginRequest
    @DeleteMapping("/api/questions/{questionId}")
    public ResponseEntity<Void> delete(@PathVariable Long questionId)
    {
        questionFacade.delete(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @LoginRequest
    @PostMapping("/api/questions/{questionId}/likes")
    public ResponseEntity<Void> addLike(@LoginUser User user,
                                        @PathVariable Long questionId)
    {
        boolean valid = questionFacade.addLike(questionId, user.getId());

        if (!valid)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
