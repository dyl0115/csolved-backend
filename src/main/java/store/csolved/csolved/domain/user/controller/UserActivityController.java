package store.csolved.csolved.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.user.controller.response.BookmarksAndPageResponse;
import store.csolved.csolved.domain.user.controller.response.RepliedPostsAndPageResponse;
import store.csolved.csolved.domain.user.controller.response.UserPostsAndPageResponse;
import store.csolved.csolved.domain.user.service.dto.result.BookmarksAndPageResult;
import store.csolved.csolved.domain.user.service.dto.result.RepliedPostsAndPageResult;
import store.csolved.csolved.domain.user.service.dto.result.UserPostsAndPageResult;
import store.csolved.csolved.domain.user.service.UserActivityService;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.page.PageInfo;

@RequestMapping("/api/users/activities")
@RequiredArgsConstructor
@RestController
public class UserActivityController
{
    private final UserActivityService userActivityService;

    //    @LoginRequest
    @GetMapping("/bookmarks")
    public BookmarksAndPageResponse getBookmarksAndPage(@LoginUser User user,
                                                        @PageInfo(type = "bookmarkPage") Long pageNumber)
    {
        BookmarksAndPageResult result = userActivityService.getBookmarksAndPage(user.getId(), pageNumber);
        return BookmarksAndPageResponse.from(result);
    }

    //    @LoginRequest
    @GetMapping("/replied-posts")
    public RepliedPostsAndPageResponse getRepliedPost(@LoginUser User user,
                                                      @PageInfo(type = "repliedPostPage") Long repliedPostPageNumber)
    {
        RepliedPostsAndPageResult result = userActivityService.getRepliedPostsAndPage(user.getId(), repliedPostPageNumber);
        return RepliedPostsAndPageResponse.from(result);
    }

    //    @LoginRequest
    @GetMapping("/posts")
    public UserPostsAndPageResponse getUserPosts(@LoginUser User user,
                                                 @PageInfo(type = "userPostPage") Long userPostPageNumber)
    {
        UserPostsAndPageResult result = userActivityService.getUserPostsAndPage(user.getId(), userPostPageNumber);
        return UserPostsAndPageResponse.from(result);
    }
}