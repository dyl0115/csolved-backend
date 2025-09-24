package store.csolved.csolved.domain.post.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler
{
    @ExceptionHandler(AlreadyLikedException.class)
    public PostExceptionResponse handleAlreadyLiked()
    {
        return new PostExceptionResponse(CommunityExceptionType.ALREADY_LIKED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public PostExceptionResponse handlePostNotFound()
    {
        return new PostExceptionResponse(CommunityExceptionType.POST_NOT_FOUND);
    }


}
