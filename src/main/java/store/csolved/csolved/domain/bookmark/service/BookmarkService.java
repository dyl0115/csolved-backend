package store.csolved.csolved.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.bookmark.mapper.BookmarkMapper;
import store.csolved.csolved.domain.bookmark.service.result.BookmarksAndPageResult;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService
{
    private final PaginationManager paginationManager;
    private final BookmarkMapper bookmarkMapper;

    @Transactional
    public void add(Long userId, Long postId)
    {
        bookmarkMapper.saveBookmark(userId, postId);
    }

    @Transactional
    public void remove(Long userId, Long postId)
    {
        bookmarkMapper.deleteBookmark(userId, postId);
    }

    public List<PostCardRecord> getBookmarks(Long userId, Pagination page)
    {
        return bookmarkMapper.getBookmarks(userId, page);
    }

    public boolean hasBookmarked(Long userId, Long postId)
    {
        return bookmarkMapper.hasBookmarked(userId, postId);
    }


    public BookmarksAndPageResult getBookmarksAndPage(Long userId,
                                                      Long pageNumber)
    {
        // DB에서 북마크 개수를 가져옴.
        Long totalPosts = bookmarkMapper.countBookmarks(userId);

        // 사용자가 요청한 페이지 번호, 북마크 개수를 사용하여 페이지 정보를 생성
        Pagination bookmarksPage = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 DB에서 필요한 북마크만 조회.
        List<PostCardRecord> posts = bookmarkMapper.getBookmarks(userId, bookmarksPage);

        return BookmarksAndPageResult.from(totalPosts, posts, bookmarksPage);
    }
}
