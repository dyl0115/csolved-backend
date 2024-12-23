package store.csolved.csolved.user.exceptions;

public class DuplicateEmailException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "이미 회원가입 된 이메일입니다.";

    public DuplicateEmailException()
    {
        super(DEFAULT_MESSAGE);
    }

    public DuplicateEmailException(Throwable cause)
    {
        super(DEFAULT_MESSAGE, cause);
    }
}
