package store.csolved.csolved.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.bookmark.service.result.BookmarksAndPageResult;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Data
@Builder
public class BookmarksAndPageResponse
{
    private Long totalPosts;
    private List<PostCardRecord> posts;
    private Pagination pagination;

    public static BookmarksAndPageResponse from(BookmarksAndPageResult result)
    {
        return BookmarksAndPageResponse.builder()
                .totalPosts(result.getTotalPosts())
                .posts(result.getPosts())
                .pagination(result.getPagination())
                .build();
    }
}
