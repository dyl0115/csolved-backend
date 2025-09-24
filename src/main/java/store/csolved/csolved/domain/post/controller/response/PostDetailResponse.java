package store.csolved.csolved.domain.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostDetail;

@Getter
@Builder
public class PostDetailResponse
{
    private PostDetail post;

    public static PostDetailResponse from(PostDetail postDetail)
    {
        return PostDetailResponse.builder()
                .post(postDetail)
                .build();
    }
}
