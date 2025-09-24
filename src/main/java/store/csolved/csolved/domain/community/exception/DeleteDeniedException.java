package store.csolved.csolved.domain.community.exception;

public class DeleteDeniedException extends RuntimeException
{
    public DeleteDeniedException()
    {
        super(CommunityExceptionType.DELETE_DENIED.getMessage());
    }
}
