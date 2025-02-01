package store.csolved.csolved.domain.post.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;

@Getter
@Builder
public class QuestionListViewModel
{
    private Pagination page;
    private List<Category> categories;
    private List<Post> questions;

    public static QuestionListViewModel of(Pagination page,
                                           List<Category> categories,
                                           List<Post> questions)
    {
        return QuestionListViewModel.builder()
                .page(page)
                .categories(categories)
                .questions(questions)
                .build();
    }
}