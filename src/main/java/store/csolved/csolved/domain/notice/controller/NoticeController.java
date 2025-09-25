package store.csolved.csolved.domain.notice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.comment.controller.request.CommentCreateRequest;
import store.csolved.csolved.domain.notice.controller.request.NoticeCreateRequest;
import store.csolved.csolved.domain.notice.controller.response.NoticeListResponse;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.utils.search.SearchInfo;
import store.csolved.csolved.utils.search.Searching;

@RequiredArgsConstructor
@Controller
public class NoticeController
{
    public final static String VIEWS_NOTICE_CREATE_FORM = "/views/notice/create";
    public final static String VIEWS_NOTICE_UPDATE_FORM = "/views/notice/update";
    public final static String VIEWS_NOTICE_LIST = "/views/notice/list";
    public final static String VIEWS_NOTICE_DETAIL = "/views/notice/detail";

//    private final NoticeFacade noticeFacade;


    @LoginRequest
    @PostMapping("/notice")
    public String processCreate(@Valid @ModelAttribute("createForm") NoticeCreateRequest form,
                                BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_NOTICE_CREATE_FORM;
        }

//        noticeFacade.save(form);
        return "redirect:/notices?page=1";
    }

    @LoginRequest
    @GetMapping("/notices")
    public String getNotices(@PageInfo Long page,
                             @SearchInfo Searching search,
                             Model model)
    {
//        NoticeListResponse viewModel = noticeFacade.getNotices(page, search);
//        model.addAttribute("noticeListViewModel", viewModel);
        return VIEWS_NOTICE_LIST;
    }

    @LoginRequest
    @GetMapping("/notice/{postId}")
    public String viewNotice(@PathVariable Long postId,
                             Model model)
    {
//        model.addAttribute("noticeDetails", noticeFacade.viewNotice(postId));
//        model.addAttribute("answerCreateForm", AnswerCreateRequest.empty());
        model.addAttribute("commentCreateForm", CommentCreateRequest.empty());
        return VIEWS_NOTICE_DETAIL;
    }

    @LoginRequest
    @GetMapping("/notice/{postId}/read")
    public String getNotice(@PathVariable Long postId,
                            Model model)
    {
//        model.addAttribute("noticeDetails", noticeFacade.getNotice(postId));
//        model.addAttribute("answerCreateForm", AnswerCreateRequest.empty());
//        model.addAttribute("commentCreateForm", CommentCreateRequest.empty());
        return VIEWS_NOTICE_DETAIL;
    }



    @LoginRequest
    @PutMapping("/notice/{postId}")
    public String processUpdate(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("updateForm") NoticeCreateRequest form,
                                BindingResult result)
    {
        if (result.hasErrors())
        {
            return VIEWS_NOTICE_UPDATE_FORM;
        }

//        noticeFacade.update(postId, form);
        return "redirect:/notices?page=1";
    }
}
