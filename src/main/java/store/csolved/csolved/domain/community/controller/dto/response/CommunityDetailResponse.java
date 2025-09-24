package store.csolved.csolved.domain.community.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.AnswerWithComments;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.community.Community;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CommunityDetailResponse
{
    private Community post;

    public static CommunityDetailResponse from(Community community)
    {
        return CommunityDetailResponse.builder()
                .post(community)
                .build();
    }
}
