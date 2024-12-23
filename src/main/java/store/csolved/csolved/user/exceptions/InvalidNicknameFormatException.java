package store.csolved.csolved.user.exceptions;

public class InvalidNicknameFormatException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "닉네임은 최대 20자까지 가능합니다.";

    public InvalidNicknameFormatException()
    {
        super(DEFAULT_MESSAGE);
    }

    public InvalidNicknameFormatException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
