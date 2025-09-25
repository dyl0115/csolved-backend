package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.notice.exception.NoticeNotFoundException;
import store.csolved.csolved.domain.notice.mapper.NoticeMapper;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;
import store.csolved.csolved.domain.notice.service.result.NoticeCardResult;
import store.csolved.csolved.domain.notice.service.result.NoticeDetailResult;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeQueryService
{
    private final NoticeMapper noticeMapper;

    public List<NoticeCardResult> getNotices(Pagination page, Searching search)
    {
        List<NoticeCardRecord> noticeCards = noticeMapper.getNotices(page, search);
        return NoticeCardResult.from(noticeCards);
    }

    @Transactional
    public NoticeDetailResult getNoticeWithIncreaseView(Long noticeId)
    {
        NoticeDetailRecord notice = noticeMapper.getNotice(noticeId);

        if (notice == null)
        {
            throw new NoticeNotFoundException();
        }

        noticeMapper.increaseView(noticeId);
        return NoticeDetailResult.from(notice);
    }

    public Long countNotices(Searching search)
    {
        return noticeMapper.countNotices(search);
    }
}
