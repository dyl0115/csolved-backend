package store.csolved.csolved.domain.auth.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.user.mapper.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
public class SignInResponse
{
    int status;
    String message;
    User principal;
    LocalDateTime timestamp;

    public static SignInResponse success(User principal)
    {
        return SignInResponse.builder()
                .status(201)
                .message("회원가입이 완료되었습니다.")
                .principal(principal)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
