package store.csolved.csolved.domain.answer.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerExceptionResponse
{
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public AnswerExceptionResponse(AnswerExceptionType exceptionType)
    {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
