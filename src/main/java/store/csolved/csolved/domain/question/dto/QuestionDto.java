package store.csolved.csolved.domain.question.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Data
public class QuestionDto
{
    private Long id;

    private Question question;

    private List<Tag> tags;

    private Long answerCount;
}
