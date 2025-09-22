package store.csolved.csolved.domain.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.community.controller.dto.response.CommunityLikeResponse;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.community.controller.form.CommunityCreateRequest;
import store.csolved.csolved.domain.community.controller.view_model.CommunityCreateUpdateVM;
import store.csolved.csolved.domain.community.controller.dto.response.CommunityDetailResponse;
import store.csolved.csolved.domain.community.controller.dto.response.CommunityListResponse;
import store.csolved.csolved.domain.community.service.CommunityFacade;
import store.csolved.csolved.utils.filter.FilterInfo;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.SortInfo;
import store.csolved.csolved.utils.sort.Sorting;

@RequiredArgsConstructor
@RestController
public class CommunityController
{
    private final CommunityFacade communityFacade;

    //    @LoginRequest
    @GetMapping("/api/communities")
    public CommunityListResponse getCommunityPosts(@PageInfo Long page,
                                                   @SortInfo Sorting sort,
                                                   @FilterInfo Filtering filter,
                                                   @SearchInfo Searching search)
    {
        return communityFacade.getCommunityPosts(page, sort, filter, search);
    }

    //    @LoginRequest
    @GetMapping("/api/community/{postId}")
    public CommunityDetailResponse getPost(@LoginUser User user,
                                           @PathVariable Long postId)
    {
        return communityFacade.getCommunityPost(user.getId(), postId);
    }

    @LoginRequest
    @PostMapping("/api/community/like/{postId}")
    public CommunityLikeResponse addLike(@LoginUser User user,
                                         @PathVariable Long postId)
    {
        communityFacade.addLike(postId, user.getId());
        return CommunityLikeResponse.success();
    }

    @LoginRequest
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long postId)
    {
        communityFacade.delete(postId);
    }

//    @LoginRequest
//    @GetMapping("/community/{postId}/read")
//    public String getPost(@LoginUser User user,
//                          @PathVariable Long postId,
//                          Model model)
//    {
//        CommunityDetailResponse post = communityFacade.getPost(user.getId(), postId);
//        model.addAttribute("communityPostDetails", post);
//        model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
//        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
//        return null;
//    }

    //    @LoginRequest
    @PostMapping("/api/community")
    public void processCreate(@Valid @RequestBody CommunityCreateRequest request)
    {
        communityFacade.save(request);
    }

    @LoginRequest
    @PutMapping("/community/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") CommunityCreateRequest form,
                                BindingResult result,
                                Model model)
    {
        if (result.hasErrors())
        {
            CommunityCreateUpdateVM viewModel = communityFacade.initUpdate();
            model.addAttribute("updateVM", viewModel);
            return null;
        }

        communityFacade.update(postId, form);
        return "redirect:/communities?page=1";
    }
}
