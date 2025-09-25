package store.csolved.csolved.domain.notice.exception;

public class NoticeNotFoundException extends RuntimeException
{
    public NoticeNotFoundException()
    {
        super(NoticeExceptionType.NOTICE_NOT_FOUND.getMessage());
    }
}
