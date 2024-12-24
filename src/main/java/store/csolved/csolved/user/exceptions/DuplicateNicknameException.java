package store.csolved.csolved.user.exceptions;

public class DuplicateNicknameException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "이미 존재하는 닉네임 입니다. 다른 닉네임으로 정해주세요.";

    public DuplicateNicknameException()
    {
        super(DEFAULT_MESSAGE);
    }

    public DuplicateNicknameException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
