package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;

import java.util.List;

@Getter
@Builder
public class QuestionCreateVM
{
    private List<Category> categories;

    public static QuestionCreateVM from(List<Category> categories)
    {
        return QuestionCreateVM.builder()
                .categories(categories)
                .build();
    }
}
