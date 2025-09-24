package store.csolved.csolved.domain.post.exception;

public class UpdateDeniedException extends RuntimeException
{
    public UpdateDeniedException()
    {
        super(CommunityExceptionType.UPDATE_DENIED.getMessage());
    }
}
