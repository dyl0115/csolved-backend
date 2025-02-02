package store.csolved.csolved.domain.post.controller.question.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;

import java.util.List;

@Getter
@Builder
public class QuestionCreateUpdateVM
{
    private List<Category> categories;

    public static QuestionCreateUpdateVM from(List<Category> categories)
    {
        return QuestionCreateUpdateVM.builder()
                .categories(categories)
                .build();
    }
}
