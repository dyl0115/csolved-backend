package store.csolved.csolved.domain.post.exception;

public class AlreadyLikedException extends RuntimeException
{
    public AlreadyLikedException()
    {
        super(PostExceptionType.ALREADY_LIKED.getMessage());
    }
}
