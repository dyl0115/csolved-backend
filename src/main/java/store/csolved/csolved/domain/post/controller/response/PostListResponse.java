package store.csolved.csolved.domain.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.global.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class PostListResponse
{
    private Pagination pagination;
    private List<PostCardRecord> posts;

    public static PostListResponse from(Pagination pagination,
                                        List<PostCardRecord> posts)
    {
        return PostListResponse.builder()
                .pagination(pagination)
                .posts(posts)
                .build();
    }
}
