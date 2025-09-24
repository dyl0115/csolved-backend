package store.csolved.csolved.domain.post.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.Post;

@Getter
@Builder
public class PostDetailResponse
{
    private Post post;

    public static PostDetailResponse from(Post post)
    {
        return PostDetailResponse.builder()
                .post(post)
                .build();
    }
}
