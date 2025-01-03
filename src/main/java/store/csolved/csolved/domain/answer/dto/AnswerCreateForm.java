package store.csolved.csolved.domain.answer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import store.csolved.csolved.domain.answer.Answer;

@Data
public class AnswerCreateForm
{
    private Long questionId;

    private Long userId;

    private Boolean anonymous;

    @NotBlank(message = "공백은 허용하지 않습니다.")
    private String content;

    public Answer toAnswer()
    {
        return Answer.create(
                questionId,
                userId,
                anonymous,
                content);
    }
}
