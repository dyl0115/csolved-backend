package store.csolved.csolved.domain.notice.mapper.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;
import store.csolved.csolved.domain.notice.service.command.NoticeCreateCommand;
import store.csolved.csolved.domain.notice.service.command.NoticeUpdateCommand;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity
{
    private String title;
    private String content;
    private Long authorId;
    private boolean isPinned;

    public static Notice from(NoticeCreateCommand command)
    {
        return Notice.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .isPinned(command.isPinned())
                .build();
    }

    public static Notice from(NoticeUpdateCommand command)
    {
        return Notice.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .isPinned(command.isPinned())
                .build();
    }
}