package store.csolved.csolved.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.domain.bookmark.mapper.BookmarkMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService
{
    private final BookmarkMapper bookmarkMapper;

    @Transactional
    public void save(Long userId, Long postId)
    {
        bookmarkMapper.saveBookmark(userId, postId);
    }

    @Transactional
    public void delete(Long userId, Long postId)
    {
        bookmarkMapper.deleteBookmark(userId, postId);
    }

    public List<Bookmark> getBookmarks(Long userId)
    {
        return bookmarkMapper.getBookmarks(userId);
    }

    public boolean hasBookmarked(Long userId, Long postId)
    {
        return bookmarkMapper.hasBookmarked(userId, postId);
    }
}
