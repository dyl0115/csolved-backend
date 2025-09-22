package store.csolved.csolved.domain.community.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class CommunityListResponse
{
    private int status;
    private Pagination pagination;
    private List<Category> categories;
    private List<Community> posts;

    public static CommunityListResponse from(Pagination page,
                                             List<Category> categories,
                                             List<Community> communities)
    {
        return CommunityListResponse.builder()
                .status(200)
                .pagination(page)
                .categories(categories)
                .posts(communities)
                .build();
    }
}
