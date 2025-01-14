package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.common.page.Page;
import store.csolved.csolved.domain.question.service.dto.QuestionSummaryDTO;

import java.util.List;

@Getter
@Builder
public class QuestionListViewModel
{
    private Page page;
    private List<QuestionSummaryDTO> questionSummaries;

    public static QuestionListViewModel from(Page page,
                                             List<QuestionSummaryDTO> questionSummaries)
    {
        return QuestionListViewModel.builder()
                .page(page)
                .questionSummaries(questionSummaries)
                .build();
    }
}
