package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;

import java.util.List;

@Getter
@Builder
public class QuestionUpdateVM
{
    private List<Category> categories;

    public static QuestionUpdateVM from(List<Category> categories)
    {
        return QuestionUpdateVM.builder()
                .categories(categories)
                .build();
    }
}
