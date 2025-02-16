package store.csolved.csolved.domain.user.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class BookmarksAndPage
{
    private List<Bookmark> bookmarks;
    private Pagination page;

    public static BookmarksAndPage from(List<Bookmark> bookmarks,
                                        Pagination page)
    {
        return BookmarksAndPage.builder()
                .bookmarks(bookmarks)
                .page(page)
                .build();
    }
}
