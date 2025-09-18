package store.csolved.csolved.domain.auth.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AuthExceptionHandler
{
    @ExceptionHandler(DuplicateEmailException.class)
    public AuthExceptionResponse handleDuplicateEmail()
    {
        return new AuthExceptionResponse(AuthExceptionType.DUPLICATE_EMAIL);
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public AuthExceptionResponse handleDuplicateNickname()
    {
        return new AuthExceptionResponse(AuthExceptionType.DUPLICATE_NICKNAME);
    }
}
