package store.csolved.csolved.domain.notice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.comment.controller.request.CommentCreateRequest;
import store.csolved.csolved.domain.notice.controller.request.NoticeCreateRequest;
import store.csolved.csolved.domain.notice.controller.request.NoticeUpdateRequest;
import store.csolved.csolved.domain.notice.controller.response.NoticeCardResponse;
import store.csolved.csolved.domain.notice.controller.response.NoticeDetailResponse;
import store.csolved.csolved.domain.notice.controller.response.NoticeListResponse;
import store.csolved.csolved.domain.notice.service.NoticeCommandService;
import store.csolved.csolved.domain.notice.service.NoticeQueryService;
import store.csolved.csolved.domain.notice.service.command.NoticeCreateCommand;
import store.csolved.csolved.domain.notice.service.command.NoticeUpdateCommand;
import store.csolved.csolved.domain.notice.service.result.NoticeCardResult;
import store.csolved.csolved.domain.notice.service.result.NoticeDetailResult;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class NoticeController
{
    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;
    private final PaginationManager paginationManager;

    @PostMapping("/notice")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@LoginUser User user,
                     @Valid @RequestBody NoticeCreateRequest request)
    {
        noticeCommandService.save(user.getAdmin(), NoticeCreateCommand.from(request));
    }

    @PutMapping("/notice/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("noticeId") Long noticeId,
                       @LoginUser User user,
                       @Valid @RequestBody NoticeUpdateRequest request)
    {
        noticeCommandService.update(user.getId(), user.getAdmin(), noticeId, NoticeUpdateCommand.from(request));
    }

    @DeleteMapping("/notice/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long noticeId,
                       @LoginUser User user)
    {
        noticeCommandService.delete(user.getId(), user.getAdmin(), noticeId);
    }

    @GetMapping("/notices")
    public NoticeListResponse getNotices(@PageInfo Long page,
                                         @SearchInfo Searching search)
    {
        Long totalNotices = noticeQueryService.countNotices(search);
        Pagination pagination = paginationManager.createPagination(page, totalNotices);
        List<NoticeCardResult> results = noticeQueryService.getNotices(pagination, search);

        return NoticeListResponse.from(pagination, results);
    }

    @GetMapping("/notice/{noticeId}")
    public NoticeDetailResponse getNoticeWithViewIncrease(@PathVariable Long noticeId)
    {
        NoticeDetailResult result = noticeQueryService.getNoticeWithIncreaseView(noticeId);
        return NoticeDetailResponse.from(result);
    }
}