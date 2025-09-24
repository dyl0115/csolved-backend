package store.csolved.csolved.domain.auth.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignOutResponse
{
    int status;
    LocalDateTime timestamp;

    public static SignOutResponse success()
    {
        return SignOutResponse.builder()
                .status(204)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
