package store.csolved.csolved.domain.auth.exception;

public class InvalidSessionException extends RuntimeException
{
    public InvalidSessionException()
    {
        super(AuthExceptionType.INVALID_SESSION.getMessage());
    }
}
