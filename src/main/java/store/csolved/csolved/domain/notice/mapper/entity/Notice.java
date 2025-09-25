package store.csolved.csolved.domain.notice.mapper.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity
{
    private String title;
    private String content;
    private Long authorId;
    private boolean isPinned;
    private Long views;
}