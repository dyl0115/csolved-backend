package store.csolved.csolved.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AuthExceptionResponse
{
    String code;
    String message;
    LocalDateTime timestamp;

    public AuthExceptionResponse(AuthExceptionType exceptionType)
    {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
