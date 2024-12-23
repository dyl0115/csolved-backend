package store.csolved.csolved.user.exceptions;

public class InvalidEmailFormatException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "이메일 형식이 올바르지 않습니다.";

    public InvalidEmailFormatException()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidEmailFormatException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
