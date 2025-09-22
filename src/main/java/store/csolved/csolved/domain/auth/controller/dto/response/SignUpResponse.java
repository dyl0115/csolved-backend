package store.csolved.csolved.domain.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignUpResponse
{
    int status;
    String message;
    LocalDateTime timestamp;

    public static SignUpResponse success()
    {
        return SignUpResponse.builder()
                .status(201)
                .message("회원가입이 완료되었습니다.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
