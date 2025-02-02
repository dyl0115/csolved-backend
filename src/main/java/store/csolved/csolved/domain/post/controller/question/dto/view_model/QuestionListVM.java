package store.csolved.csolved.domain.post.controller.question.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;

@Getter
@Builder
public class QuestionListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<Post> posts;

    public static QuestionListVM from(Pagination page,
                                      List<Category> categories,
                                      List<Post> questions)
    {
        return QuestionListVM.builder()
                .page(page)
                .categories(categories)
                .posts(questions)
                .build();
    }
}