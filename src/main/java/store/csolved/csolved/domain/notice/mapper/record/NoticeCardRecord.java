package store.csolved.csolved.domain.notice.mapper.record;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeCardRecord
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
