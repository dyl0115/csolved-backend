package store.csolved.csolved.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.post.controller.dto.request.PostUpdateRequest;
import store.csolved.csolved.domain.post.service.PostCommandService;
import store.csolved.csolved.domain.post.service.PostLikeService;
import store.csolved.csolved.domain.post.service.PostQueryService;
import store.csolved.csolved.domain.post.service.command.PostCreateCommand;
import store.csolved.csolved.domain.post.service.command.PostUpdateCommand;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.post.controller.dto.request.PostCreateRequest;
import store.csolved.csolved.domain.post.controller.dto.response.PostDetailResponse;
import store.csolved.csolved.domain.post.controller.dto.response.PostListResponse;
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
@RequestMapping("/api")
public class PostController
{
    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;
    private final PostLikeService postLikeService;

    //    @LoginRequest
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody PostCreateRequest request)
    {
        postCommandService.save(PostCreateCommand.from(request));
    }

    //    @LoginRequest
    @GetMapping("/posts")
    public PostListResponse getPosts(@PageInfo Long page,
                                     @SortInfo Sorting sort,
                                     @FilterInfo Filtering filter,
                                     @SearchInfo Searching search)
    {
        return postQueryService.getPosts(page, sort, filter, search);
    }


    //    @LoginRequest
    @GetMapping("/post/{postId}")
    public PostDetailResponse getPost(@PathVariable Long postId)
    {
        return postQueryService.getPost(postId);
    }


    //    @LoginRequest
    @PostMapping("/post/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@LoginUser User user,
                        @PathVariable Long postId)
    {
        postLikeService.addLike(postId, user.getId());
    }

    //    @LoginRequest
    @PutMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@LoginUser User user,
                       @PathVariable("postId") Long postId,
                       @Valid @RequestBody PostUpdateRequest request)
    {
        postCommandService.update(user.getId(),
                postId,
                PostUpdateCommand.from(request));
    }

    //    @LoginRequest
    @DeleteMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@LoginUser User user,
                       @PathVariable Long postId)
    {
        postCommandService.delete(user.getId(), postId);
    }
}
