package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.service.dto.CategoryDTO;

import java.util.List;

@Getter
@Builder
public class QuestionCreateUpdateVM
{
    private List<CategoryDTO> categories;

    public static QuestionCreateUpdateVM of(List<CategoryDTO> categories)
    {
        return QuestionCreateUpdateVM.builder()
                .categories(categories)
                .build();
    }
}
