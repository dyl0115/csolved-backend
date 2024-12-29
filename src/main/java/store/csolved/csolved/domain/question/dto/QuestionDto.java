package store.csolved.csolved.domain.question.dto;

import lombok.Getter;
import lombok.Setter;
import store.csolved.csolved.domain.question.Question;

@Getter
@Setter
public class QuestionDto
{
    private Question question;

    private String tags;
    
    private Long answerCount;
}
