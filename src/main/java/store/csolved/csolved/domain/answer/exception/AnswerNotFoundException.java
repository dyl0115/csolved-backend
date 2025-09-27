package store.csolved.csolved.domain.answer.exception;

public class AnswerNotFoundException extends RuntimeException
{
    public AnswerNotFoundException()
    {
        super(AnswerExceptionType.ANSWER_NOT_FOUND.getMessage());
    }
}
