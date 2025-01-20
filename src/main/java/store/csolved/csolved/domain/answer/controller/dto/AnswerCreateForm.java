package store.csolved.csolved.domain.answer.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import store.csolved.csolved.domain.answer.entity.Answer;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerCreateForm
{
    private Long questionId;
    private Long authorId;
    private Boolean anonymous;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    public static AnswerCreateForm empty()
    {
        return AnswerCreateForm.builder().build();
    }

    public Answer toAnswer()
    {
        return Answer.builder()
                .questionId(questionId)
                .authorId(authorId)
                .anonymous(anonymous)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
