package store.csolved.csolved.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.bookmark.PostCard;
import store.csolved.csolved.domain.user.service.dto.result.BookmarksAndPageResult;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Data
@Builder
public class BookmarksAndPageResponse
{
    private Long totalPosts;
    private List<PostCard> posts;
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
