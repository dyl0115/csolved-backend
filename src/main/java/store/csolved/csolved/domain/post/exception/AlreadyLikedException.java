package store.csolved.csolved.domain.post.exception;

public class AlreadyLikedException extends RuntimeException
{
    public AlreadyLikedException()
    {
        super(CommunityExceptionType.ALREADY_LIKED.getMessage());
    }
}
