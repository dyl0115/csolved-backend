package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.question.entity.Question;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class QuestionDetailVM
{
    private Question question;
    private List<AnswerWithComments> answers;

    public static QuestionDetailVM of(Question question,
                                      List<Answer> answers,
                                      Map<Long, List<Comment>> comments)
    {
        return QuestionDetailVM.builder()
                .answers(AnswerWithComments.from(answers, comments))
                .question(question)
                .build();
    }
}