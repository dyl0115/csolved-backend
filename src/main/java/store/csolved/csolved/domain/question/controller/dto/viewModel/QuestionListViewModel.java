package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.common.page.Pagination;
import store.csolved.csolved.domain.category.service.dto.CategoryDTO;
import store.csolved.csolved.domain.question.entity.Question;

import java.util.List;

@Getter
@Builder
public class QuestionListViewModel
{
    private Pagination page;
    private List<CategoryDTO> categories;
    private List<Question> questions;

    public static QuestionListViewModel of(Pagination page,
                                           List<CategoryDTO> categories,
                                           List<Question> questions)
    {
        return QuestionListViewModel.builder()
                .page(page)
                .categories(categories)
                .questions(questions)
                .build();
    }
}
