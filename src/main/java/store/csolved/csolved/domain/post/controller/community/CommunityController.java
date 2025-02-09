package store.csolved.csolved.domain.post.controller.community;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.post.controller.community.dto.form.CommunityCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.community.dto.view_model.CommunityCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.community.dto.view_model.CommunityListVM;
import store.csolved.csolved.domain.post.facade.community.CommunityFacade;
import store.csolved.csolved.domain.search.filter.FilterInfo;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.PageInfo;
import store.csolved.csolved.domain.search.search.SearchInfo;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.SortInfo;
import store.csolved.csolved.domain.search.sort.Sorting;

@RequiredArgsConstructor
@Controller
public class CommunityController
{
    public final static String VIEWS_COMMUNITY_CREATE_FORM = "/views/domain/community/create";
    public final static String VIEWS_COMMUNITY_UPDATE_FORM = "/views/domain/community/update";
    public final static String VIEWS_COMMUNITY_LIST = "/views/domain/community/list";
    public final static String VIEWS_COMMUNITY_DETAIL = "/views/domain/community/detail";

    private final CommunityFacade communityFacade;

    @LoginRequest
    @GetMapping("/communities")
    public String getCommunityPosts(@PageInfo Long page,
                                    @SortInfo Sorting sort,
                                    @FilterInfo Filtering filter,
                                    @SearchInfo Searching search,
                                    Model model)
    {
        CommunityListVM viewModel = communityFacade.getCommunityPosts(page, sort, filter, search);
        model.addAttribute("communityPostListViewModel", viewModel);

//        System.out.println("communityController :: getCommunityPosts");
//        viewModel.getPosts().forEach(post ->
//        {
//            System.out.println(post.getTitle() + ": ");
//            post.getTags().forEach(tag -> System.out.print(tag.getName() + "////"));
//        });
        return VIEWS_COMMUNITY_LIST;
    }

    @LoginRequest
    @GetMapping("/community/{postId}")
    public String getCommunityPost(@PathVariable Long postId,
                                   Model model)
    {
        model.addAttribute("communityPostDetails", communityFacade.getCommunityPost(postId));
        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return VIEWS_COMMUNITY_DETAIL;
    }

    @LoginRequest
    @GetMapping("/community/createForm")
    public String initCreate(Model model)
    {
        CommunityCreateUpdateVM viewModel = communityFacade.initCreate();
        model.addAttribute("createVM", viewModel);
        model.addAttribute("createForm", CommunityCreateUpdateForm.empty());
        return VIEWS_COMMUNITY_CREATE_FORM;
    }

    @LoginRequest
    @PostMapping("/community")
    public String processCreate(@Valid @ModelAttribute("createForm") CommunityCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        System.out.println("communityController :: processCreate");
        System.out.println("tagSize: " + form.getTagList().size());
        System.out.println("tagList");
        form.getTagList().forEach(tag -> System.out.println("    " + tag.getName()));

        if (result.hasErrors())
        {
            CommunityCreateUpdateVM viewModel = communityFacade.initCreate();
            model.addAttribute("createVM", viewModel);
            return VIEWS_COMMUNITY_CREATE_FORM;
        }
        else
        {
            communityFacade.save(form);
            return "redirect:/communities?page=1";
        }
    }

    @LoginRequest
    @GetMapping("/community/{postId}/updateForm")
    public String initUpdate(@PathVariable Long postId,
                             Model model)
    {
        CommunityCreateUpdateVM viewModel = communityFacade.initUpdate(postId);
        model.addAttribute("updateVM", viewModel);
        CommunityCreateUpdateForm form = communityFacade.initUpdateForm(postId);
        model.addAttribute("updateForm", form);
        return VIEWS_COMMUNITY_UPDATE_FORM;
    }

    @LoginRequest
    @PutMapping("/community/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") CommunityCreateUpdateForm form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CommunityCreateUpdateVM viewModel = communityFacade.initUpdate(postId);
            model.addAttribute("updateVM", viewModel);
            return VIEWS_COMMUNITY_UPDATE_FORM;
        }

        communityFacade.update(postId, form);
        return "redirect:/communities?page=1";
    }
}
