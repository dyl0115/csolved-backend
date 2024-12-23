package store.csolved.csolved.user.exceptions;

public class EmptyPasswordException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "비밀번호는 공백일 수 없습니다.";

    public EmptyPasswordException()
    {
        super(DEFAULT_MESSAGE);
    }

    public EmptyPasswordException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
