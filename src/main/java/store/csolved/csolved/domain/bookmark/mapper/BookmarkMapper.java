package store.csolved.csolved.domain.bookmark.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.common.Post;
import store.csolved.csolved.domain.bookmark.Bookmark;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Mapper
public interface BookmarkMapper
{
    void saveBookmark(Long userId, Long postId);

    void deleteBookmark(Long userId, Long postId);

    List<Bookmark> getBookmarks(Long userId);

    Long countBookmarks(Long userId, Long postId);

    boolean hasBookmarked(Long userId, Long postId);
}