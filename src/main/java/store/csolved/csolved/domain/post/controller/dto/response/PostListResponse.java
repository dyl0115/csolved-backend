package store.csolved.csolved.domain.post.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.Post;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class PostListResponse
{
    private Pagination pagination;
    private List<Post> posts;

    public static PostListResponse from(Pagination page,
                                        List<Post> communities)
    {
        return PostListResponse.builder()
                .pagination(page)
                .posts(communities)
                .build();
    }
}
