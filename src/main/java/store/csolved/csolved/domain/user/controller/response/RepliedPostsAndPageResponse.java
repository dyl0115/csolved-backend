package store.csolved.csolved.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.domain.post.service.result.RepliedPostsAndPageResult;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Data
@Builder
public class RepliedPostsAndPageResponse
{
    private Long totalPosts;
    private List<PostCard> posts;
    private Pagination pagination;

    public static RepliedPostsAndPageResponse from(RepliedPostsAndPageResult result)
    {
        return RepliedPostsAndPageResponse.builder()
                .totalPosts(result.getTotalPosts())
                .posts(result.getPosts())
                .pagination(result.getPagination())
                .build();
    }
}
