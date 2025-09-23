package store.csolved.csolved.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.bookmark.PostCard;
import store.csolved.csolved.domain.user.service.dto.result.UserPostsAndPageResult;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Data
@Builder
public class UserPostsAndPageResponse
{
    private Long totalPosts;
    private List<PostCard> posts;
    private Pagination pagination;

    public static UserPostsAndPageResponse from(UserPostsAndPageResult result)
    {
        return UserPostsAndPageResponse.builder()
                .totalPosts(result.getTotalPosts())
                .posts(result.getPosts())
                .pagination(result.getPagination())
                .build();
    }
}
