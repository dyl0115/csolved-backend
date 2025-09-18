package store.csolved.csolved.domain.auth.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse
{
    int status;
    String message;

    public static SignupResponse success()
    {
        return SignupResponse.builder()
                .status(201)
                .message("회원가입이 완료되었습니다.")
                .build();
    }
}
