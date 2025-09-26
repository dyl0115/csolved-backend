package store.csolved.csolved.domain.notice.service.command;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.notice.controller.request.NoticeCreateRequest;

@Data
@Builder
public class NoticeCreateCommand
{
    private String title;
    private String content;
    private Long authorId;
    private boolean isPinned;

    public static NoticeCreateCommand from(NoticeCreateRequest request)
    {
        return NoticeCreateCommand.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .isPinned(request.isPinned())
                .build();
    }
}
