package store.csolved.csolved.domain.auth.exception;

public class DuplicateEmailException extends RuntimeException
{
    public DuplicateEmailException()
    {
        super(AuthExceptionType.DUPLICATE_EMAIL.getMessage());
    }
}
