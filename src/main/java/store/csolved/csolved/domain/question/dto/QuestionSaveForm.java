package store.csolved.csolved.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.user.User;

@Data
public class QuestionSaveForm
{
    private User user;

    @NotNull
    private Boolean isAnonymous;

    @NotNull
    private Long categoryId;

    private String tags;

    @NotBlank
    @Size(min = 2, max = 30)
    private String title;

    @NotBlank
    @Size(min = 1)
    private String content;

    public Question toQuestion()
    {
        return Question.create(user.getId(), title, content, categoryId);
    }
}
