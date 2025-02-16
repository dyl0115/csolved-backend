package store.csolved.csolved.domain.user.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class UserActivityVM
{
    private BookmarksAndPage bookmarksAndPage;

    public static UserActivityVM from(BookmarksAndPage bookmarksAndPage)
    {
        return UserActivityVM.builder()
                .bookmarksAndPage(bookmarksAndPage)
                .build();
    }
}
