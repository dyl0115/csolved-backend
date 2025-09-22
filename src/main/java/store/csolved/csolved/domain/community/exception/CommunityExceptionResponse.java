package store.csolved.csolved.domain.community.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommunityExceptionResponse
{
    int status;
    String code;
    String message;
    LocalDateTime timestamp;

    public CommunityExceptionResponse(CommunityExceptionType exceptionType)
    {
        this.status = 405;
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
