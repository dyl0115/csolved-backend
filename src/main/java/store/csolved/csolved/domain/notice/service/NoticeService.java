package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.notice.Notice;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

import static store.csolved.csolved.common.PostType.NOTICE;

@RequiredArgsConstructor
@Service
public class NoticeService
{
    private final NoticeMapper noticeMapper;

    @Transactional
    public void save(Notice notice)
    {
        noticeMapper.saveNotice(NOTICE.getCode(), notice);
    }

    public Long countNotice(Searching search)
    {
        return noticeMapper.countNotices(NOTICE.getCode(), search);

    }

    public List<Notice> getNotices(Pagination page, Searching search)
    {
        return noticeMapper.getNotices(NOTICE.getCode(), page, search);
    }

    public Notice getNotice(Long noticeId)
    {
        return noticeMapper.getNotice(noticeId);
    }

    @Transactional
    public Notice viewNotice(Long noticeId)
    {
        noticeMapper.increaseView(noticeId);
        return noticeMapper.getNotice(noticeId);
    }

    @Transactional
    public void update(Long noticeId, Notice notice)
    {
        noticeMapper.updateNotice(noticeId, notice);
    }

    @Transactional
    public void delete(Long noticeId)
    {
        noticeMapper.deleteNotice(noticeId);
    }

    @Transactional
    public boolean addLike(Long noticeId, Long userId)
    {
        if (noticeMapper.hasUserLiked(noticeId, userId))
        {
            return false;
        }

        noticeMapper.addUserLike(noticeId, userId);
        noticeMapper.increaseLikes(noticeId);
        return true;
    }
}
