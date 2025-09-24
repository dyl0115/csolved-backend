package store.csolved.csolved.domain.post.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.Post;

@Getter
@Builder
public class CommunityDetailResponse
{
    private Post post;

    public static CommunityDetailResponse from(Post post)
    {
        return CommunityDetailResponse.builder()
                .post(post)
                .build();
    }
}
