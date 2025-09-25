package store.csolved.csolved.domain.notice.mapper.record;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeDetailRecord
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private String content;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
