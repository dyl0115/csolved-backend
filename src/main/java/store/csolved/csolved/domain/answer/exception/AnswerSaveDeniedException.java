package store.csolved.csolved.domain.answer.exception;

public class AnswerSaveDeniedException extends RuntimeException
{
    public AnswerSaveDeniedException()
    {
        super(AnswerExceptionType.SAVE_DENIED.getMessage());
    }
}
