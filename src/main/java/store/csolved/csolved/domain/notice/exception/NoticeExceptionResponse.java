package store.csolved.csolved.domain.notice.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeExceptionResponse
{
    private int status;
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public NoticeExceptionResponse(NoticeExceptionType exceptionType)
    {
        this.status = 405;
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
