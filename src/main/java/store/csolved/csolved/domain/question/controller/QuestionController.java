package store.csolved.csolved.domain.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;
import store.csolved.csolved.utils.filter.FilterInfo;
import store.csolved.csolved.utils.sort.SortInfo;
import store.csolved.csolved.domain.answer.controller.form.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.form.CommentCreateForm;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.domain.question.controller.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.view_model.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.question.controller.view_model.QuestionListVM;
import store.csolved.csolved.domain.question.service.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    public final static String VIEWS_QUESTION_CREATE_FORM = "/views/question/create";
    public final static String VIEWS_QUESTION_UPDATE_FORM = "/views/question/update";
    public final static String VIEWS_QUESTION_LIST = "/views/question/list";
    public final static String VIEWS_QUESTION_DETAIL = "/views/question/detail";

    private final QuestionFacade questionFacade;

    @LoginRequest
    @GetMapping("/questions")
    public String getQuestions(@PageInfo Long page,
                               @SortInfo Sorting sort,
                               @FilterInfo Filtering filter,
                               @SearchInfo Searching search,
                               Model model)
    {
        QuestionListVM viewModel = questionFacade.getQuestions(page, sort, filter, search);
        model.addAttribute("questionListViewModel", viewModel);
        return VIEWS_QUESTION_LIST;
    }

    @LoginRequest
    @GetMapping("/question/{postId}")
    public String getQuestion(@LoginUser User user,
                              @PathVariable Long postId,
                              Model model)
    {
        model.addAttribute("questionDetails", questionFacade.getQuestion(user.getId(), postId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return VIEWS_QUESTION_DETAIL;
    }

    @LoginRequest
    @GetMapping("/question/createForm")
    public String initCreate(Model model)
    {
        QuestionCreateUpdateVM viewModel = questionFacade.initCreateUpdate();
        QuestionCreateUpdateForm form = QuestionCreateUpdateForm.empty();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", form);
        return VIEWS_QUESTION_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/question")
    public String processCreate(@Valid @ModelAttribute("createForm") QuestionCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            QuestionCreateUpdateVM viewModel = questionFacade.initCreateUpdate();
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
    @GetMapping("/question/{postId}/updateForm")
    public String initUpdate(@PathVariable Long postId,
                             Model model)
    {
        QuestionCreateUpdateVM viewModel = questionFacade.initCreateUpdate();
        model.addAttribute("updateVM", viewModel);
        QuestionCreateUpdateForm form = questionFacade.initUpdateForm(postId);
        model.addAttribute("updateForm", form);
        return VIEWS_QUESTION_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/question/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") QuestionCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            QuestionCreateUpdateVM viewModel = questionFacade.initCreateUpdate();
            model.addAttribute("updateVM", viewModel);
            return VIEWS_QUESTION_UPDATE_FORM;
        }

        questionFacade.update(postId, form);
        return "redirect:/questions?page=1";
    }
}