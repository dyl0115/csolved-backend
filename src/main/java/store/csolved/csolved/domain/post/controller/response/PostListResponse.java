package store.csolved.csolved.domain.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class PostListResponse
{
    private Pagination pagination;
    private List<PostCard> posts;

    public static PostListResponse from(Pagination pagination,
                                        List<PostCard> posts)
    {
        return PostListResponse.builder()
                .pagination(pagination)
                .posts(posts)
                .build();
    }
}
