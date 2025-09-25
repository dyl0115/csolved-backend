package store.csolved.csolved.domain.notice.exception;

public class NoticeDeleteDeniedException extends RuntimeException
{
    public NoticeDeleteDeniedException()
    {
        super(NoticeExceptionType.DELETE_DENIED.getMessage());
    }
}
