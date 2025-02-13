package store.csolved.csolved.domain.post.controller.community.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.post.entity.Community;
import store.csolved.csolved.domain.search.page.Pagination;

import java.util.List;

@Getter
@Builder
public class CommunityListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<Community> communities;

    public static CommunityListVM from(Pagination page,
                                       List<Category> categories,
                                       List<Community> communities)
    {
        return CommunityListVM.builder()
                .page(page)
                .categories(categories)
                .communities(communities)
                .build();
    }
}
