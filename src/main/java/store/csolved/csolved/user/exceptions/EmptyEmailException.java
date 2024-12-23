package store.csolved.csolved.user.exceptions;

public class EmptyEmailException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "이메일은 공백일 수 없습니다.";

    public EmptyEmailException()
    {
        super(DEFAULT_MESSAGE);
    }

    public EmptyEmailException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
