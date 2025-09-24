package store.csolved.csolved.domain.auth.exception;

public class InvalidPasswordException extends RuntimeException
{
    public InvalidPasswordException()
    {
        super(AuthExceptionType.INVALID_PASSWORD.getMessage());
    }
}
