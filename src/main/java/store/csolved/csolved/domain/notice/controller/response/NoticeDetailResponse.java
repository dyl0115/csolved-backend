package store.csolved.csolved.domain.notice.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.notice.service.result.NoticeDetailResult;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeDetailResponse
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorNickname;
    private String content;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static NoticeDetailResponse from(NoticeDetailResult result)
    {
        return NoticeDetailResponse.builder()
                .id(result.getId())
                .title(result.getTitle())
                .authorId(result.getAuthorId())
                .authorNickname(result.getAuthorNickname())
                .content(result.getContent())
                .views(result.getViews())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .build();
    }
}
