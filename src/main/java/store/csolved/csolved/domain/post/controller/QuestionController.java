package store.csolved.csolved.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.search.SearchInfo;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.search.filter.FilterInfo;
import store.csolved.csolved.domain.search.sort.SortInfo;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.search.page.PageInfo;
import store.csolved.csolved.domain.post.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionCreateVM;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionUpdateVM;
import store.csolved.csolved.domain.post.facade.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    public final static String VIEWS_QUESTION_CREATE_FORM = "views/domain/question/create";
    public final static String VIEWS_QUESTION_UPDATE_FORM = "views/domain/question/update";
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
        model.addAttribute("questionDetails", questionFacade.getQuestion(questionId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return VIEWS_QUESTION_DETAIL;
    }

    @LoginRequest
    @GetMapping("/questions/create")
    public String initCreate(Model model)
    {
        QuestionCreateVM viewModel = questionFacade.initCreate();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", QuestionCreateUpdateForm.empty());
        return VIEWS_QUESTION_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/questions/create")
    public String processCreate(@Valid @ModelAttribute("createForm") QuestionCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            QuestionCreateVM viewModel = questionFacade.initCreate();
            model.addAttribute("createVM", viewModel);
            return VIEWS_QUESTION_CREATE_FORM;
        }
        else
        {
            questionFacade.save(form);
            return "redirect:/questions?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/questions/{questionId}/update")
    public String initUpdate(@PathVariable Long questionId,
                             Model model)
    {
        QuestionUpdateVM viewModel = questionFacade.initUpdate(questionId);
        model.addAttribute("updateVM", viewModel);
        QuestionCreateUpdateForm form = questionFacade.initUpdateForm(questionId);
        model.addAttribute("updateForm", form);
        return VIEWS_QUESTION_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/questions/{questionId}/update")
    public String processUpdate(@PathVariable("questionId") Long questionId,
                                @Valid @ModelAttribute("updateForm") QuestionCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            QuestionUpdateVM viewModel = questionFacade.initUpdate(questionId);
            model.addAttribute("updateVM", viewModel);
            return VIEWS_QUESTION_UPDATE_FORM;
        }

        questionFacade.update(questionId, form);
        return "redirect:/questions?page=1";
    }
}