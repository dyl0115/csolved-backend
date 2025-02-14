package store.csolved.csolved.domain.bookmark.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.common.Post;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Mapper
public interface BookmarkMapper
{
    void saveBookmark(Long postId, Long userId);

    List<Post> getBookmarks(Long userId);

    void deleteBookmark(Long postId);
}