package store.csolved.csolved.domain.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;
import store.csolved.csolved.domain.notice.mapper.record.NoticeCardRecord;
import store.csolved.csolved.domain.notice.mapper.record.NoticeDetailRecord;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

@Mapper
public interface NoticeMapper
{
    void saveNotice(Notice notice);

    void updateNotice(Long noticeId, Notice notice);

    void deleteNotice(Long noticeId);

    List<NoticeCardRecord> getNotices(@Param("page") Pagination page,
                                      @Param("search") Searching search);

    NoticeDetailRecord getNotice(Long noticeId);

    Long countNotices(@Param("search") Searching search);

    void increaseView(Long noticeId);
}
