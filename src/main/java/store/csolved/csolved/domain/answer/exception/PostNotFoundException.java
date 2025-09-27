package store.csolved.csolved.domain.answer.exception;

public class PostNotFoundException extends RuntimeException
{
    public PostNotFoundException()
    {
        super(AnswerExceptionType.POST_NOT_FOUND.getMessage());
    }
}
