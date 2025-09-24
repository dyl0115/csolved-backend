package store.csolved.csolved.domain.community.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommunityExceptionHandler
{
    @ExceptionHandler(AlreadyLikedException.class)
    public CommunityExceptionResponse handleAlreadyLiked()
    {
        return new CommunityExceptionResponse(CommunityExceptionType.ALREADY_LIKED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public CommunityExceptionResponse handlePostNotFound()
    {
        return new CommunityExceptionResponse(CommunityExceptionType.POST_NOT_FOUND);
    }


}
