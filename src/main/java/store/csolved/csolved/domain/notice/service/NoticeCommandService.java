package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.notice.exception.NoticeAdminOnlyException;
import store.csolved.csolved.domain.notice.exception.NoticeNotFoundException;
import store.csolved.csolved.domain.notice.exception.NoticeSaveDeniedException;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class NoticeCommandService
{
    private final NoticeMapper noticeMapper;

    @Transactional
    public void save(boolean userIsAdmin, Notice notice)
    {
        if (!userIsAdmin)
        {
            throw new NoticeAdminOnlyException();
        }

        noticeMapper.saveNotice(notice);
    }

    @Transactional
    public void update(Long userId, boolean userIsAdmin, Long noticeId, Notice notice)
    {
        Long authorId = noticeMapper.getAuthorId(noticeId);

        if (authorId == null)
        {
            throw new NoticeNotFoundException();
        }

        if (!Objects.equals(userId, authorId))
        {
            throw new NoticeSaveDeniedException();
        }

        if (!userIsAdmin)
        {
            throw new NoticeAdminOnlyException();
        }

        noticeMapper.updateNotice(noticeId, notice);
    }

    @Transactional
    public void delete(Long userId, boolean userIsAdmin, Long noticeId)
    {
        Long authorId = noticeMapper.getAuthorId(noticeId);

        if (authorId == null)
        {
            throw new NoticeNotFoundException();
        }

        if (!Objects.equals(userId, authorId))
        {
            throw new NoticeSaveDeniedException();
        }

        if (!userIsAdmin)
        {
            throw new NoticeAdminOnlyException();
        }

        noticeMapper.deleteNotice(noticeId);
    }
}
