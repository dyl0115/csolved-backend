package store.csolved.csolved.domain.post.controller.code_review.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;

import java.util.List;

@Getter
@Builder
public class CodeReviewCreateUpdateVM
{
    private List<Category> categories;

    public static CodeReviewCreateUpdateVM from(List<Category> categories)
    {
        return CodeReviewCreateUpdateVM.builder()
                .categories(categories)
                .build();
    }
}
