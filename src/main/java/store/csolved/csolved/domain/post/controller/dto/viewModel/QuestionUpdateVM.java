package store.csolved.csolved.domain.post.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;

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
