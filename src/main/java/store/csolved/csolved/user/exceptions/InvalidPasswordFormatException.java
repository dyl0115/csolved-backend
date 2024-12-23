package store.csolved.csolved.user.exceptions;

public class InvalidPasswordFormatException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "최소 하나의 숫자, 특수문자, 대문자 포함 등의 규칙 추가";

    public InvalidPasswordFormatException()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidPasswordFormatException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
