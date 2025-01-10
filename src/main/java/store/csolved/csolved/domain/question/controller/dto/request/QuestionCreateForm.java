package store.csolved.csolved.domain.question.controller.dto.request;

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

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private Boolean anonymous;

    private List<Category> categoryList;

    @NotNull(message = "카테고리를 하나 선택해주세요.")
    private Long categoryId;

    @NotBlank(message = "태그는 반드시 하나 이상 있어야 합니다.")
    private String tags;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 80, message = "제목은 최소 2글자에서 80자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public Question toQuestion()
    {
        return Question.create(
                null,
                authorId,
                anonymous,
                title,
                content,
                categoryId);
    }
}
