package store.csolved.csolved.domain.post.controller.community.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;

import java.util.List;

@Getter
@Builder
public class CommunityCreateUpdateVM
{
    private List<Category> categories;

    public static CommunityCreateUpdateVM from(List<Category> categories)
    {
        return CommunityCreateUpdateVM.builder()
                .categories(categories)
                .build();
    }
}
