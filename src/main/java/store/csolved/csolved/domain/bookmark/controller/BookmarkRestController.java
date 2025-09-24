package store.csolved.csolved.domain.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.bookmark.controller.response.BookmarkStatusResponse;
import store.csolved.csolved.domain.bookmark.service.BookmarkService;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.service.dto.result.BookmarksAndPageResult;
import store.csolved.csolved.domain.user.service.UserActivityService;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.page.PageInfo;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class BookmarkRestController
{
    private final BookmarkService bookmarkService;

    //    @LoginRequest
    @PostMapping("/post/{postId}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void add(@LoginUser User user,
                    @PathVariable Long postId)
    {
        bookmarkService.add(user.getId(), postId);
    }

    @GetMapping("/post/{postId}/bookmark")
    public BookmarkStatusResponse getStatus(@LoginUser User user,
                                            @PathVariable Long postId)
    {
        boolean bookmarked = bookmarkService.hasBookmarked(user.getId(), postId);
        return BookmarkStatusResponse.from(bookmarked);
    }

    //    @LoginRequest
    @DeleteMapping("/post/{postId}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@LoginUser User user,
                       @PathVariable Long postId)
    {
        bookmarkService.remove(user.getId(), postId);
    }
}
