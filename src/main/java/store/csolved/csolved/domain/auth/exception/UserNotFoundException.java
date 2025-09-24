package store.csolved.csolved.domain.auth.exception;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException()
    {
        super(AuthExceptionType.USER_NOT_FOUND.getMessage());
    }
}
