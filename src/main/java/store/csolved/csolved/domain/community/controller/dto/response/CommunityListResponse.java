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
    private Pagination pagination;
    private List<Community> posts;

    public static CommunityListResponse from(Pagination page,
                                             List<Community> communities)
    {
        return CommunityListResponse.builder()
                .pagination(page)
                .posts(communities)
                .build();
    }
}
