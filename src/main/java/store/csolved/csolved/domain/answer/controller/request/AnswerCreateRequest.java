package store.csolved.csolved.domain.answer.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerCreateRequest
{
    private Long postId;
    private Long authorId;
    private Boolean anonymous;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
    
}
