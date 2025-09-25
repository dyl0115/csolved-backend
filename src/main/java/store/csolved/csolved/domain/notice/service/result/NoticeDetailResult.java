package store.csolved.csolved.domain.notice.service.result;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeDetailResult
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private String content;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static NoticeDetailResult from(NoticeDetailRecord record)
    {
        return NoticeDetailResult.builder()
                .id(record.getId())
                .title(record.getTitle())
                .authorId(record.getAuthorId())
                .authorNickname(record.getAuthorNickname())
                .content(record.getContent())
                .views(record.getViews())
                .createdAt(record.getCreatedAt())
                .modifiedAt(record.getModifiedAt())
                .build();
    }
}
