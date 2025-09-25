package store.csolved.csolved.domain.post.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler
{
    @ExceptionHandler(AlreadyLikedException.class)
    public PostExceptionResponse handleAlreadyLiked()
    {
        return new PostExceptionResponse(PostExceptionType.ALREADY_LIKED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public PostExceptionResponse handlePostNotFound()
    {
        return new PostExceptionResponse(PostExceptionType.POST_NOT_FOUND);
    }


}
