package store.csolved.csolved.domain.notice.exception;

public class NoticeUpdateDeniedException extends RuntimeException
{
    public NoticeUpdateDeniedException()
    {
        super(NoticeExceptionType.UPDATE_DENIED.getMessage());
    }
}
