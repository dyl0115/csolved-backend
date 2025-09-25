package store.csolved.csolved.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerCommandService;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.notice.controller.request.NoticeCreateRequest;
import store.csolved.csolved.domain.notice.controller.response.NoticeDetailResponse;
import store.csolved.csolved.domain.notice.controller.response.NoticeListResponse;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeFacade
{
    private final NoticeService noticeService;
    private final AnswerCommandService answerService;
    private final PaginationManager paginationUtils;
    private final CommentService commentService;

    @Transactional
    public void save(NoticeCreateRequest form)
    {
//        noticeService.save(form.getNotice());
    }

    public NoticeListResponse getNotices(Long pageNumber, Searching search)
    {
        Long total = noticeService.countNotice(search);
        Pagination page = paginationUtils.createPagination(pageNumber, total);
//        List<Notice> notices = noticeService.getNotices(page, search);
//        return NoticeListResponse.from(page, notices);
        return null;
    }

    public NoticeDetailResponse viewNotice(Long postId)
    {
//        Notice notice = noticeService.getNotice(postId);
//        List<Answer> answers = answerService.getAnswersWithComments(postId);
//        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
//        return NoticeDetailResponse.from(notice, answers, comments);
        return null;
    }

    public NoticeDetailResponse getNotice(Long postId)
    {
//        Notice notice = noticeService.getNotice(postId);
//        List<Answer> answers = answerService.getAnswersWithComments(postId);
//        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
//        return NoticeDetailResponse.from(notice, answers, comments);
        return null;
    }

    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }

    @Transactional
    public void update(Long postId, NoticeCreateRequest form)
    {
//        noticeService.update(postId, form.getNotice());
    }

    public NoticeCreateRequest initUpdateForm(Long postId)
    {
//        Notice notice = noticeService.getNotice(postId);
//        return NoticeCreateUpdateRequest.from(notice);
        return null;
    }

    @Transactional
    public void delete(Long postId)
    {
        noticeService.delete(postId);
    }
}
