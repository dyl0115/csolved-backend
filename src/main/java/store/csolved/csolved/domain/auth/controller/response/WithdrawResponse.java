package store.csolved.csolved.domain.auth.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawResponse
{
    int status;
    String message;

    public static WithdrawResponse success()
    {
        return WithdrawResponse.builder()
                .status(200)
                .message("회원탈퇴가 완료되었습니다.")
                .build();
    }
}
