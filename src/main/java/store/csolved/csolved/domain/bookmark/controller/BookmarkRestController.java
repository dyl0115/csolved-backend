package store.csolved.csolved.domain.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.csolved.csolved.domain.bookmark.service.BookmarkService;

@RequiredArgsConstructor
@Controller
public class BookmarkController
{
    private final BookmarkService bookmarkService;

    @GetMapping("/bookmark/{postId}")
    public String save(@PathVariable Long postId)
    {
        return
    }

}
