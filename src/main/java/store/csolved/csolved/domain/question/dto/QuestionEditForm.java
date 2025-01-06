package store.csolved.csolved.domain.question.dto;

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

    @NotNull
    private Boolean anonymous;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String tags;

    @NotBlank
    @Size(min = 2, max = 30)
    private String title;

    @NotBlank
    private String content;

    public Question toQuestion()
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
