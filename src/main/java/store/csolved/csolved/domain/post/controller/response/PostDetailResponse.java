package store.csolved.csolved.domain.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.mapper.record.PostDetailRecord;

@Getter
@Builder
public class PostDetailResponse
{
    private PostDetailRecord post;

    public static PostDetailResponse from(PostDetailRecord postDetail)
    {
        return PostDetailResponse.builder()
                .post(postDetail)
                .build();
    }
}
