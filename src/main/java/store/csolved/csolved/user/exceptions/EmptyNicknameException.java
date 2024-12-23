package store.csolved.csolved.user.exceptions;

public class EmptyNicknameException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "닉네임은 공백일 수 없습니다.";

    public EmptyNicknameException()
    {
        super(DEFAULT_MESSAGE);
    }

    public EmptyNicknameException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
