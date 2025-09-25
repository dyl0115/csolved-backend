package store.csolved.csolved.domain.notice.exception;

public class NoticeAdminOnlyException extends RuntimeException
{
    public NoticeAdminOnlyException()
    {
        super(NoticeExceptionType.ADMIN_ONLY.getMessage());
    }
}
