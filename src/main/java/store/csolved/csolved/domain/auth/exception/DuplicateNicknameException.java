package store.csolved.csolved.domain.auth.exception;

public class DuplicateNicknameException extends RuntimeException
{
    public DuplicateNicknameException()
    {
        super(AuthExceptionType.DUPLICATE_NICKNAME.getMessage());
    }
}
