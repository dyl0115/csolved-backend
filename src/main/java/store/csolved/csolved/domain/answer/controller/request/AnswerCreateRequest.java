package store.csolved.csolved.domain.answer.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerCreateRequest
{
    @NotNull
    private Long postId;
    @NotNull
    private Long authorId;
    private Boolean anonymous;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(max = 2000)
    private String content;

}
