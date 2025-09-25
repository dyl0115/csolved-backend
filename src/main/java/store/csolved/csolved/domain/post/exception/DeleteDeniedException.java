package store.csolved.csolved.domain.post.exception;

public class DeleteDeniedException extends RuntimeException
{
    public DeleteDeniedException()
    {
        super(PostExceptionType.DELETE_DENIED.getMessage());
    }
}
