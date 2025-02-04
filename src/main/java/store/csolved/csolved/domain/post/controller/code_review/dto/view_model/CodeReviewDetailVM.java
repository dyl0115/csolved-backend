package store.csolved.csolved.domain.post.controller.code_review.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.post.entity.code_review.CodeReview;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CodeReviewDetailVM
{
    private CodeReview post;
    private List<AnswerWithComments> answers;

    public static CodeReviewDetailVM from(CodeReview question,
                                          List<Answer> answers,
                                          Map<Long, List<Comment>> comments)
    {
        return CodeReviewDetailVM.builder()
                .answers(AnswerWithComments.from(answers, comments))
                .post(question)
                .build();
    }
}
