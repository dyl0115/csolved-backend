package store.csolved.csolved.domain.post.service.result;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.global.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class RepliedPostsAndPageResult
{
    private Long totalPosts;
    private List<PostCardRecord> posts;
    private Pagination pagination;

    public static RepliedPostsAndPageResult from(
            Long totalPosts,
            List<PostCardRecord> posts,
            Pagination page)
    {
        return RepliedPostsAndPageResult.builder()
                .totalPosts(totalPosts)
                .posts(posts)
                .pagination(page)
                .build();
    }
}
