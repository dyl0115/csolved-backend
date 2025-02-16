package store.csolved.csolved.domain.post.question.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.AnswerWithComments;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.post.question.Question;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class QuestionDetailVM
{
    private Question post;
    private boolean bookmarked;
    private List<AnswerWithComments> answers;

    public static QuestionDetailVM from(Question question,
                                        boolean bookmarked,
                                        List<Answer> answers,
                                        Map<Long, List<Comment>> comments)
    {
        return QuestionDetailVM.builder()
                .bookmarked(bookmarked)
                .answers(AnswerWithComments.from(answers, comments))
                .post(question)
                .build();
    }
}