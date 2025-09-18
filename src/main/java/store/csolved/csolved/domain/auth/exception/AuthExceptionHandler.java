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

    @ExceptionHandler(UserNotFoundException.class)
    public AuthExceptionResponse handleUserNotFound()
    {
        return new AuthExceptionResponse(AuthExceptionType.USER_NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public AuthExceptionResponse handleInvalidPassword()
    {
        return new AuthExceptionResponse(AuthExceptionType.INVALID_PASSWORD);
    }
}
