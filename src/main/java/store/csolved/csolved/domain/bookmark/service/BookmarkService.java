package store.csolved.csolved.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.bookmark.PostCard;
import store.csolved.csolved.domain.bookmark.mapper.BookmarkMapper;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService
{
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

    public List<PostCard> getBookmarks(Long userId, Pagination page)
    {
        return bookmarkMapper.getBookmarks(userId, page);
    }

    public boolean hasBookmarked(Long userId, Long postId)
    {
        return bookmarkMapper.hasBookmarked(userId, postId);
    }
}
