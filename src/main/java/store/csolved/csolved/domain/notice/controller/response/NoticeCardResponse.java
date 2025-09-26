package store.csolved.csolved.domain.notice.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.notice.service.result.NoticeCardResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NoticeCardResponse
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static NoticeCardResponse from(NoticeCardResult result)
    {
        return NoticeCardResponse.builder()
                .id(result.getId())
                .title(result.getTitle())
                .authorId(result.getAuthorId())
                .authorNickname(result.getAuthorNickname())
                .views(result.getViews())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .build();
    }

    public static List<NoticeCardResponse> from(List<NoticeCardResult> results)
    {
        return results.stream()
                .map(NoticeCardResponse::from)
                .collect(Collectors.toList());
    }
}
