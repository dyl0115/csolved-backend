package store.csolved.csolved.user.exceptions;

public class AuthenticationFailedException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "이메일 혹은 비밀번호가 잘못되었습니다. 다시 확인해 주세요.";

    public AuthenticationFailedException()
    {
        super(DEFAULT_MESSAGE);
    }

    public AuthenticationFailedException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
