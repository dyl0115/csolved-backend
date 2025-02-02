package store.csolved.csolved.domain.post.controller.community.dto.view_model;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.search.page.Pagination;

import java.util.List;

@Getter
@Builder
public class CommunityListVM
{
    private Pagination page;
    private List<Category> categories;
    private List<Post> posts;

    public static CommunityListVM from(Pagination page,
                                       List<Category> categories,
                                       List<Post> posts)
    {
        return CommunityListVM.builder()
                .page(page)
                .categories(categories)
                .posts(posts)
                .build();
    }
}
