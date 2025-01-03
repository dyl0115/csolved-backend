package store.csolved.csolved.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.question.Question;

import java.util.List;

@Data
public class QuestionCreateForm
{
    private Long authorId;

    @NotNull
    private Boolean anonymous;

    private List<Category> categoryList;

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
                authorId,
                anonymous,
                title,
                content,
                categoryId);
    }
}
