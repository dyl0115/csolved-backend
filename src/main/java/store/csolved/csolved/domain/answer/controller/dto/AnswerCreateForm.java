package store.csolved.csolved.domain.answer.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.Answer;

@Builder
@Data
public class AnswerCreateForm
{
    private Long questionId;

    private Long userId;

    @NotEmpty(message = "익명/실명 여부를 선택해 주세요.")
    private Boolean anonymous;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    public static AnswerCreateForm empty()
    {
        return AnswerCreateForm.builder().build();
    }

    public Answer toAnswer()
    {
        return Answer.create(
                questionId,
                userId,
                anonymous,
                content);
    }
}
