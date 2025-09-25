package store.csolved.csolved.domain.notice.service.result;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NoticeCardResult
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static NoticeCardResult from(NoticeCardRecord record)
    {
        return NoticeCardResult.builder()
                .id(record.getId())
                .title(record.getTitle())
                .authorId(record.getAuthorId())
                .authorNickname(record.getAuthorNickname())
                .views(record.getViews())
                .createdAt(record.getCreatedAt())
                .modifiedAt(record.getModifiedAt())
                .build();
    }

    public static List<NoticeCardResult> from(List<NoticeCardRecord> records)
    {
        return records.stream()
                .map(NoticeCardResult::from)
                .collect(Collectors.toList());
    }
}
