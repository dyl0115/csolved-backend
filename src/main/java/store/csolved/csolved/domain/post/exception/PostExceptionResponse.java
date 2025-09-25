package store.csolved.csolved.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostExceptionResponse
{
    int status;
    String code;
    String message;
    LocalDateTime timestamp;

    public PostExceptionResponse(PostExceptionType exceptionType)
    {
        this.status = 405;
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
