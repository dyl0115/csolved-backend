package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.question.entity.Question;

import java.util.List;

@Getter
@Builder
public class QuestionDetailVM
{
    private Question question;
    private List<AnswerWithComments> answers;

    public static QuestionDetailVM of(Question question,
                                      List<AnswerWithComments> answers)
    {
        return QuestionDetailVM.builder()
                .question(question)
                .answers(answers)
                .build();
    }
}