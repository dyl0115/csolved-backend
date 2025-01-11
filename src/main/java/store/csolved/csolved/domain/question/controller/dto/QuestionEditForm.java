package store.csolved.csolved.domain.question.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import store.csolved.csolved.domain.question.Question;

@Data
@AllArgsConstructor
public class QuestionEditForm
{
    private Long questionId;

    private Long authorId;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private Boolean anonymous;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotBlank(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private String tags;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public Question toCommand()
    {
        return Question.create(
                questionId,
                authorId,
                anonymous,
                title,
                content,
                categoryId);
    }
}
