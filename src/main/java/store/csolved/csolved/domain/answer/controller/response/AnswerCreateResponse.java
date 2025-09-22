package store.csolved.csolved.domain.answer.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnswerCreateResponse
{
    int status;
    String message;
    LocalDateTime timestamp;

    public static AnswerCreateResponse success()
    {
        return AnswerCreateResponse.builder()
                .status(200)
                .message("댓글 등록이 완료 되었습니다.")
                .build();
    }
}
