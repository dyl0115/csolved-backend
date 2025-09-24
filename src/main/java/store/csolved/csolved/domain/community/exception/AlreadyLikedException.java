package store.csolved.csolved.domain.community.exception;

public class AlreadyLikedException extends RuntimeException
{
    public AlreadyLikedException()
    {
        super(CommunityExceptionType.ALREADY_LIKED.getMessage());
    }
}
