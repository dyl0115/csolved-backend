package store.csolved.csolved.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(CsolvedException.class)
    public ResponseEntity<ExceptionResponse> handleException(CsolvedException exception)
    {
        ExceptionResponse exceptionResponse
                = ExceptionResponse.from(exception.getCode());

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse);
    }
}
