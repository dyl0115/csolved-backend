package store.csolved.csolved.domain.auth.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.user.mapper.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CheckSessionResponse
{
    int status;
    String message;
    User principal;
    LocalDateTime timestamp;

    public static CheckSessionResponse success(User principal)
    {
        return CheckSessionResponse.builder()
                .status(201)
                .message("")
                .principal(principal)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
