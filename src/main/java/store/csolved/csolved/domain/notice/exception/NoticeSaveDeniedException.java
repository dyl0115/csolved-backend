package store.csolved.csolved.domain.notice.exception;

public class NoticeSaveDeniedException extends RuntimeException
{
    public NoticeSaveDeniedException()
    {
        super(NoticeExceptionType.SAVE_DENIED.getMessage());
    }
}
