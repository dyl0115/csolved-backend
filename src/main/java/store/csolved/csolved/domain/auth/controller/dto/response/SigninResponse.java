package store.csolved.csolved.domain.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SigninResponse
{
    int code;
    String message;
    LocalDateTime timestamp;

    public static SigninResponse success()
    {
        return SigninResponse.builder()
                .code(200)
                .message("로그인이 완료되었습니다.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
