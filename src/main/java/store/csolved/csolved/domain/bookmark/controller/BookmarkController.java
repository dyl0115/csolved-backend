package store.csolved.csolved.domain.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.domain.bookmark.service.BookmarkService;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookmarkController
{
    public static final String VIEWS_BOOKMARK_LIST = "/views/bookmark/list";

    private final BookmarkService bookmarkService;

    @LoginRequest
    @GetMapping("/bookmarks")
    public String getBookmarks(@LoginUser User user,
                               Model model)
    {
        List<Bookmark> bookmarks = bookmarkService.getBookmarks(user.getId());
        model.addAttribute("bookmarks", bookmarks);
        return VIEWS_BOOKMARK_LIST;
    }
}
