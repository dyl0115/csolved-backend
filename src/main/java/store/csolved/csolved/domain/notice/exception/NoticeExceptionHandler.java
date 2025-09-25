package store.csolved.csolved.domain.notice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoticeExceptionHandler
{
    @ExceptionHandler(NoticeNotFoundException.class)
    public NoticeExceptionResponse handleNoticeNotFound()
    {
        return new NoticeExceptionResponse(NoticeExceptionType.NOTICE_NOT_FOUND);
    }

    @ExceptionHandler(NoticeUpdateDeniedException.class)
    public NoticeExceptionResponse handleUpdateDenied()
    {
        return new NoticeExceptionResponse(NoticeExceptionType.UPDATE_DENIED);
    }

    @ExceptionHandler(NoticeDeleteDeniedException.class)
    public NoticeExceptionResponse handleNoticeDeleteDenied()
    {
        return new NoticeExceptionResponse(NoticeExceptionType.DELETE_DENIED);
    }

    @ExceptionHandler(NoticeAdminOnlyException.class)
    public NoticeExceptionResponse handleAdminOnly()
    {
        return new NoticeExceptionResponse(NoticeExceptionType.ADMIN_ONLY);
    }

    @ExceptionHandler(NoticeSaveDeniedException.class)
    public NoticeExceptionResponse handleSaveDenied()
    {
        return new NoticeExceptionResponse(NoticeExceptionType.SAVE_DENIED);
    }
}