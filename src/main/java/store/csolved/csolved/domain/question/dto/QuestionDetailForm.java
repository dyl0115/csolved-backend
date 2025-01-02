package store.csolved.csolved.domain.question.dto;

import lombok.Data;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.dto.AnswerDto;
import store.csolved.csolved.domain.question.Question;

import java.util.List;

@Data
public class QuestionDetailForm
{
    private QuestionDto question;

    private List<AnswerDto> answers;
}
