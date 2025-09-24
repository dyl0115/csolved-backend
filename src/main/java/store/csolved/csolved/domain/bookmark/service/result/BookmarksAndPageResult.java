package store.csolved.csolved.domain.bookmark.service.result;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class BookmarksAndPageResult
{
    private Long totalPosts;
    private List<PostCard> posts;
    private Pagination pagination;

    public static BookmarksAndPageResult from(Long totalPosts,
                                              List<PostCard> bookmarks,
                                              Pagination pagination)
    {
        return BookmarksAndPageResult.builder()
                .totalPosts(totalPosts)
                .posts(bookmarks)
                .pagination(pagination)
                .build();
    }
}
