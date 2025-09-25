package store.csolved.csolved.domain.notice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.notice.Notice;

@Getter
@Builder
public class NoticeCreateUpdateRequest
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    private Long authorId;

    @NotNull(message = "실명/익명 여부를 선택해주세요.")
    private boolean anonymous;

    public static NoticeCreateUpdateRequest empty()
    {
        return NoticeCreateUpdateRequest.builder()
                .anonymous(false)
                .build();
    }

    public static NoticeCreateUpdateRequest from(store.csolved.csolved.domain.notice.Notice notice)
    {
        return NoticeCreateUpdateRequest.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .authorId(notice.getAuthorId())
                .anonymous(notice.isAnonymous())
                .build();
    }

    public Notice getNotice()
    {
        return Notice.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .anonymous(anonymous)
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .build();
    }
}
