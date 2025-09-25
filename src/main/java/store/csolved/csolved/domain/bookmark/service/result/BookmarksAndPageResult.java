package store.csolved.csolved.domain.bookmark.service.result;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class BookmarksAndPageResult
{
    private Long totalPosts;
    private List<PostCardRecord> posts;
    private Pagination pagination;

    public static BookmarksAndPageResult from(Long totalPosts,
                                              List<PostCardRecord> bookmarks,
                                              Pagination pagination)
    {
        return BookmarksAndPageResult.builder()
                .totalPosts(totalPosts)
                .posts(bookmarks)
                .pagination(pagination)
                .build();
    }
}
