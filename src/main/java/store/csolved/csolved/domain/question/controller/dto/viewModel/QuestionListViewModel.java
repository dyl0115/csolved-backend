package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.common.page.PageDetailDTO;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailDTO;
import store.csolved.csolved.domain.question.service.dto.QuestionSummaryDTO;

import java.util.List;

@Getter
@Builder
public class QuestionListViewModel
{
    private PageDetailDTO page;
    private List<CategoryDetailDTO> categories;
    private List<QuestionSummaryDTO> questionSummaries;

    public static QuestionListViewModel from(PageDetailDTO page,
                                             List<CategoryDetailDTO> categories,
                                             List<QuestionSummaryDTO> questionSummaries)
    {
        return QuestionListViewModel.builder()
                .page(page)
                .categories(categories)
                .questionSummaries(questionSummaries)
                .build();
    }
}
