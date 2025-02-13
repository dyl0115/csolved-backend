package store.csolved.csolved.domain.post.controller.question;

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
import store.csolved.csolved.domain.post.controller.question.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.question.view_model.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.question.view_model.QuestionListVM;
import store.csolved.csolved.domain.post.facade.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class QuestionController
{
    public final static String VIEWS_QUESTION_CREATE_FORM = "/views/domain/question/create";
    public final static String VIEWS_QUESTION_UPDATE_FORM = "/views/domain/question/update";
    public final static String VIEWS_QUESTION_LIST = "/views/domain/question/list";
    public final static String VIEWS_QUESTION_DETAIL = "/views/domain/question/detail";

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
    public String getQuestion(@PathVariable Long postId,
                              Model model)
    {
        model.addAttribute("questionDetails", questionFacade.getQuestion(postId));
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