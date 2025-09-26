package store.csolved.csolved.domain.notice.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.notice.service.result.NoticeCardResult;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Getter
@Builder
public class NoticeListResponse
{
    private Pagination pagination;
    private List<NoticeCardResponse> notices;

    public static NoticeListResponse from(Pagination pagination,
                                          List<NoticeCardResult> results)
    {
        return NoticeListResponse.builder()
                .pagination(pagination)
                .notices(NoticeCardResponse.from(results))
                .build();
    }
}
