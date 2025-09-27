package store.csolved.csolved.domain.answer.exception;


public class AnswerDeleteDeniedException extends RuntimeException
{
    public AnswerDeleteDeniedException()
    {
        super(AnswerExceptionType.DELETE_DENIED.getMessage());
    }
}
