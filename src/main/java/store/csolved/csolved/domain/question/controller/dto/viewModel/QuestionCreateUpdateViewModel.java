package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailDTO;

import java.util.List;

@Getter
@Builder
public class QuestionCreateUpdateViewModel
{
    private List<CategoryDetailDTO> categories;

    public static QuestionCreateUpdateViewModel from(List<CategoryDetailDTO> categories)
    {
        return QuestionCreateUpdateViewModel.builder()
                .categories(categories)
                .build();
    }
}
