package store.csolved.csolved.domain.notice.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.notice.Notice;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class NoticeListResponse
{
    private Pagination page;
    private List<Notice> posts;

    public static NoticeListResponse from(Pagination page,
                                    List<Notice> posts)
    {
        return NoticeListResponse.builder()
                .page(page)
                .posts(posts)
                .build();
    }
}
