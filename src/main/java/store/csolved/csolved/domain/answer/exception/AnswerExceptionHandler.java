package store.csolved.csolved.domain.answer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnswerExceptionHandler
{
    @ExceptionHandler(AnswerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AnswerExceptionResponse handleAnswerNotFound()
    {
        return new AnswerExceptionResponse(AnswerExceptionType.ANSWER_NOT_FOUND);
    }

    @ExceptionHandler(AnswerSaveDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AnswerExceptionResponse handleAnswerSaveDenied()
    {
        return new AnswerExceptionResponse((AnswerExceptionType.SAVE_DENIED));
    }



}
