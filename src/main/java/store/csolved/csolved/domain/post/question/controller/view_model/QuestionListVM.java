package store.csolved.csolved.domain.post.question.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.question.Question;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Getter
@Builder
public class QuestionListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<Question> posts;

    public static QuestionListVM from(Pagination page,
                                      List<Category> categories,
                                      List<Question> questions)
    {
        return QuestionListVM.builder()
                .page(page)
                .categories(categories)
                .posts(questions)
                .build();
    }
}