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
    private boolean bookmarked;
    private List<AnswerWithComments> answers;

    public static CommunityDetailResponse from(Community community,
                                               boolean bookmarked,
                                               List<Answer> answers,
                                               Map<Long, List<Comment>> comments)
    {
        return CommunityDetailResponse.builder()
                .bookmarked(bookmarked)
                .answers(AnswerWithComments.from(answers, comments))
                .post(community)
                .build();
    }
}
