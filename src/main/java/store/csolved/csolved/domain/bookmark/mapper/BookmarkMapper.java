package store.csolved.csolved.domain.bookmark.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Mapper
public interface BookmarkMapper
{
    void saveBookmark(Long userId, Long postId);

    void deleteBookmark(Long userId, Long postId);

    List<PostCardRecord> getBookmarkedPosts(Long userId, Pagination page);

    Long countBookmarks(Long userId);

    boolean hasBookmarked(Long userId, Long postId);
}