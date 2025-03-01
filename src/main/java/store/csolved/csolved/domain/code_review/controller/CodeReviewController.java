package store.csolved.csolved.domain.code_review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.form.AnswerCreateForm;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.code_review.controller.form.CodeReviewCreateUpdateForm;
import store.csolved.csolved.domain.code_review.controller.view_model.CodeReviewCreateUpdateVM;
import store.csolved.csolved.domain.code_review.controller.view_model.CodeReviewListVM;
import store.csolved.csolved.domain.code_review.service.CodeReviewFacade;
import store.csolved.csolved.utils.filter.FilterInfo;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.SortInfo;
import store.csolved.csolved.utils.sort.Sorting;

@RequiredArgsConstructor
@Controller
public class CodeReviewController
{
    public final static String VIEWS_CODE_REVIEW_CREATE_FORM = "/views/code_review/create";
    public final static String VIEWS_CODE_REVIEW_UPDATE_FORM = "/views/code_review/update";
    public final static String VIEWS_CODE_REVIEW_LIST = "/views/code_review/list";
    public final static String VIEWS_CODE_REVIEW_DETAIL = "/views/code_review/detail";

    private final CodeReviewFacade codeReviewFacade;

    @LoginRequest
    @GetMapping("/code-review/createForm")
    public String initCreate(Model model)
    {
        CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
        CodeReviewCreateUpdateForm form = codeReviewFacade.initCreate();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", form);
        return VIEWS_CODE_REVIEW_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/code-review")
    public String processCreate(@Valid @ModelAttribute("createForm") CodeReviewCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
            model.addAttribute("createVM", viewModel);
            return VIEWS_CODE_REVIEW_CREATE_FORM;
        }

        codeReviewFacade.save(form);
        return "redirect:/code-reviews?page=1";
    }

    @LoginRequest
    @GetMapping("/code-reviews")
    public String getCodeReviews(@PageInfo Long page,
                                 @SortInfo Sorting sort,
                                 @FilterInfo Filtering filter,
                                 @SearchInfo Searching search,
                                 Model model)

    {
        CodeReviewListVM viewModel = codeReviewFacade.getCodeReviews(page, sort, filter, search);
        model.addAttribute("codeReviewListViewModel", viewModel);
        return VIEWS_CODE_REVIEW_LIST;
    }

    @LoginRequest
    @GetMapping("/code-review/{postId}")
    public String viewCodeReview(@LoginUser User user,
                                 @PathVariable Long postId,
                                 Model model)
    {
        model.addAttribute("codeReviewDetails", codeReviewFacade.viewCodeReview(user.getId(), postId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", AnswerCreateForm.empty());
        return VIEWS_CODE_REVIEW_DETAIL;
    }

    @LoginRequest
    @GetMapping("/code-review/{postId}/read")
    public String getCodeReview(@LoginUser User user,
                                @PathVariable Long postId,
                                Model model)
    {
        model.addAttribute("codeReviewDetails", codeReviewFacade.getCodeReview(user.getId(), postId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", AnswerCreateForm.empty());
        return VIEWS_CODE_REVIEW_DETAIL;
    }

    @LoginRequest
    @GetMapping("/code-review/{postId}/updateForm")
    public String initUpdate(@PathVariable Long postId,
                             Model model)
    {
        CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
        model.addAttribute("updateVM", viewModel);
        CodeReviewCreateUpdateForm form = codeReviewFacade.initUpdateForm(postId);
        model.addAttribute("updateForm", form);
        return VIEWS_CODE_REVIEW_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/code-review/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") CodeReviewCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CodeReviewCreateUpdateVM viewModel = codeReviewFacade.initCreateUpdate();
            model.addAttribute("updateVM", viewModel);
            return VIEWS_CODE_REVIEW_UPDATE_FORM;
        }

        codeReviewFacade.update(postId, form);
        return "redirect:/code-reviews?page=1";
    }
}
