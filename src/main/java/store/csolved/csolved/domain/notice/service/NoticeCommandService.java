package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.domain.notice.service.command.NoticeCreateCommand;
import store.csolved.csolved.domain.notice.service.command.NoticeUpdateCommand;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class NoticeCommandService
{
    private final NoticeMapper noticeMapper;

    @Transactional
    public void save(boolean userIsAdmin, NoticeCreateCommand command)
    {
        if (!userIsAdmin)
        {
            throw new CsolvedException(ExceptionCode.NOTICE_ADMIN_ONLY);
        }

        noticeMapper.saveNotice(Notice.from(command));
    }

    @Transactional
    public void update(Long userId, boolean userIsAdmin,
                       Long noticeId, NoticeUpdateCommand command)
    {
        Long authorId = noticeMapper.getAuthorId(noticeId);

        if (authorId == null)
        {
            throw new CsolvedException(ExceptionCode.NOTICE_NOT_FOUND);
        }

        if (!Objects.equals(userId, authorId))
        {
            throw new CsolvedException(ExceptionCode.NOTICE_UPDATE_DENIED);
        }

        if (!userIsAdmin)
        {
            throw new CsolvedException(ExceptionCode.NOTICE_ADMIN_ONLY);
        }

        noticeMapper.updateNotice(noticeId, Notice.from(command));
    }

    @Transactional
    public void delete(Long userId, boolean userIsAdmin, Long noticeId)
    {
        Long authorId = noticeMapper.getAuthorId(noticeId);

        if (authorId == null)
        {
            throw new CsolvedException(ExceptionCode.NOTICE_NOT_FOUND);
        }

        if (!Objects.equals(userId, authorId))
        {
            throw new CsolvedException(ExceptionCode.NOTICE_DELETE_DENIED);
        }

        if (!userIsAdmin)
        {
            throw new CsolvedException(ExceptionCode.NOTICE_ADMIN_ONLY);
        }

        noticeMapper.deleteNotice(noticeId);
    }
}
