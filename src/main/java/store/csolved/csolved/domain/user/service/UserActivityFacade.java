package store.csolved.csolved.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.domain.bookmark.mapper.BookmarkMapper;
import store.csolved.csolved.domain.user.controller.view_model.BookmarksAndPage;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserActivityFacade
{
    private final BookmarkMapper bookmarkMapper;
    private final PaginationManager paginationManager;

    public BookmarksAndPage getBookmarksAndPage(Long userId,
                                                Long pageNumber)
    {
        // DB에서 북마크 개수를 가져옴.
        Long total = bookmarkMapper.countBookmarks(userId);

        // 사용자가 요청한 페이지 번호, 북마크 개수를 사용하여 페이지 정보를 생성
        Pagination bookmarksPage = paginationManager.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB에서 필요한 북마크만 조회.
        List<Bookmark> bookmarks = bookmarkMapper.getBookmarks(userId, bookmarksPage);

        return BookmarksAndPage.from(bookmarks, bookmarksPage);
    }
}
