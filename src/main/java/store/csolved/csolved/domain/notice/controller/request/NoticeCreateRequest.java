package store.csolved.csolved.domain.notice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;

@Getter
@Builder
public class NoticeCreateRequest
{
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 최소 2글자에서 50자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull
    private Long authorId;


    public static NoticeCreateRequest from(Notice notice)
    {
        return NoticeCreateRequest.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .authorId(notice.getAuthorId())
                .build();
    }
}
