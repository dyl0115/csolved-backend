package store.csolved.csolved.domain.post.controller.question.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.entity.Question;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.category.entity.Category;

import java.util.List;

@Getter
@Builder
public class QuestionListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<Question> questions;

    public static QuestionListVM from(Pagination page,
                                      List<Category> categories,
                                      List<Question> questions)
    {
        return QuestionListVM.builder()
                .page(page)
                .categories(categories)
                .questions(questions)
                .build();
    }
}