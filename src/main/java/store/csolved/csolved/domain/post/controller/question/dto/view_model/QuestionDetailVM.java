package store.csolved.csolved.domain.post.controller.question.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class QuestionDetailVM
{
    private Post post;
    private List<AnswerWithComments> answers;

    public static QuestionDetailVM from(Post question,
                                        List<Answer> answers,
                                        Map<Long, List<Comment>> comments)
    {
        return QuestionDetailVM.builder()
                .answers(AnswerWithComments.from(answers, comments))
                .post(question)
                .build();
    }
}