package store.csolved.csolved.domain.post.code_review.controller.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.post.code_review.CodeReview;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class CodeReviewListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<CodeReview> posts;

    public static CodeReviewListVM from(Pagination page,
                                        List<Category> categories,
                                        List<CodeReview> posts)
    {
        return CodeReviewListVM.builder()
                .page(page)
                .categories(categories)
                .posts(posts)
                .build();
    }
}
