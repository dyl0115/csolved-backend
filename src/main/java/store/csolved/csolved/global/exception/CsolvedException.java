package store.csolved.csolved.global.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CsolvedException extends RuntimeException
{
    private ExceptionCode code;

    public CsolvedException(ExceptionCode exceptionCode)
    {
        this.code = exceptionCode;
    }
}
