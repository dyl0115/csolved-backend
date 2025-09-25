package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService
{
    private final NoticeMapper noticeMapper;

    @Transactional
    public void save(Notice notice)
    {
        noticeMapper.saveNotice(notice);
    }

    public Long countNotice(Searching search)
    {
        return noticeMapper.countNotices(search);

    }

    public List<NoticeCardRecord> getNotices(Pagination page, Searching search)
    {
        return noticeMapper.getNotices(page, search);
    }

    @Transactional
    public NoticeDetailRecord getNotice(Long noticeId)
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
}
