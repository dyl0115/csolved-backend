package store.csolved.csolved.domain.community.exception;

public class UpdateDeniedException extends RuntimeException
{
    public UpdateDeniedException()
    {
        super(CommunityExceptionType.UPDATE_DENIED.getMessage());
    }
}
