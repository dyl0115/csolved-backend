package store.csolved.csolved.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.post.controller.dto.request.CommunityUpdateRequest;
import store.csolved.csolved.domain.post.controller.dto.response.CommunityLikeResponse;
import store.csolved.csolved.domain.post.service.PostCommandService;
import store.csolved.csolved.domain.post.service.PostLikeService;
import store.csolved.csolved.domain.post.service.PostQueryService;
import store.csolved.csolved.domain.post.service.command.CommunityCreateCommand;
import store.csolved.csolved.domain.post.service.command.CommunityUpdateCommand;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.post.controller.dto.request.CommunityCreateRequest;
import store.csolved.csolved.domain.post.controller.dto.response.CommunityDetailResponse;
import store.csolved.csolved.domain.post.controller.dto.response.CommunityListResponse;
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
public class PostController
{
    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;
    private final PostLikeService postLikeService;


    //    @LoginRequest
    @GetMapping("/api/communities")
    public CommunityListResponse getPosts(@PageInfo Long page,
                                          @SortInfo Sorting sort,
                                          @FilterInfo Filtering filter,
                                          @SearchInfo Searching search)
    {
        return postQueryService.getCommunityPosts(page, sort, filter, search);
    }

    //    @LoginRequest
    @GetMapping("/api/community/{postId}")
    public CommunityDetailResponse getPostQueryService(@PathVariable Long postId)
    {
        return postQueryService.getCommunityPost(postId);
    }

    //    @LoginRequest
    @PostMapping("/api/community/like/{postId}")
    public CommunityLikeResponse addLike(@LoginUser User user,
                                         @PathVariable Long postId)
    {
        postLikeService.addLike(postId, user.getId());
        return CommunityLikeResponse.success();
    }

    //    @LoginRequest
    @PostMapping("/api/community")
    public void save(@Valid @RequestBody CommunityCreateRequest request)
    {
        postCommandService.save(CommunityCreateCommand.from(request));
    }

    //    @LoginRequest
    @PutMapping("/api/community/{postId}")
    public void update(@LoginUser User user,
                       @PathVariable("postId") Long postId,
                       @Valid @RequestBody CommunityUpdateRequest request)
    {
        postCommandService.update(
                user.getId(),
                postId,
                CommunityUpdateCommand.from(request));
    }

    //    @LoginRequest
    @DeleteMapping("/api/community/{postId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@LoginUser User user,
                       @PathVariable Long postId)
    {
        postCommandService.delete(user.getId(), postId);
    }
}
