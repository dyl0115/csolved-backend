package store.csolved.csolved.domain.user.service.dto.result;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.PostCard;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class UserPostsAndPageResult
{
    private Long totalPosts;
    private List<PostCard> posts;
    private Pagination pagination;

    public static UserPostsAndPageResult from(
            Long totalPosts,
            List<PostCard> posts,
            Pagination page)
    {
        return UserPostsAndPageResult.builder()
                .totalPosts(totalPosts)
                .posts(posts)
                .pagination(page)
                .build();
    }
}
