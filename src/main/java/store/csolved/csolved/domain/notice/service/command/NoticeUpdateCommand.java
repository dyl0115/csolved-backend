package store.csolved.csolved.domain.notice.service.command;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.notice.controller.request.NoticeCreateRequest;
import store.csolved.csolved.domain.notice.controller.request.NoticeUpdateRequest;

@Data
@Builder
public class NoticeUpdateCommand
{
    private String title;
    private String content;
    private Long authorId;
    private boolean isPinned;

    public static NoticeUpdateCommand from(NoticeUpdateRequest request)
    {
        return NoticeUpdateCommand.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .isPinned(request.isPinned())
                .build();
    }
}
