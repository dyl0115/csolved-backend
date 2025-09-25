package store.csolved.csolved.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.notice.service.NoticeFacade;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;
import store.csolved.csolved.domain.comment.controller.request.CommentCreateRequest;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.utils.login.LoginUser;

import static store.csolved.csolved.domain.notice.controller.NoticeController.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController
{
    private final CommentService commentService;

    // TODO: 여기 싹 수정해야함
//    @LoginRequest
//    @PostMapping("/notice/{postId}/answers/{answerId}/comment")
//    public String saveNoticeComment(@PathVariable("postId") Long postId,
//                                    @Valid @ModelAttribute("commentCreateForm") CommentCreateRequest form,
//                                    BindingResult result,
//                                    Model model)
//    {
//        if (result.hasErrors())
//        {
//            model.addAttribute("noticeDetails", noticeFacade.viewNotice(postId));
//            model.addAttribute("answerCreateForm", AnswerCreateRequest.empty());
//            model.addAttribute("commentCreateFrom", form);
//            return VIEWS_NOTICE_DETAIL;
//        }
//        commentService.saveComment(form.toCommand());
//        return "redirect:/notice/" + postId + "/read";
//    }

    //    @LoginRequest
    @PostMapping("/community/{postId}/answer/{answerId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCommunityComment(@Valid @RequestBody CommentCreateRequest request)
    {
        commentService.saveComment(request.toCommand());
    }

    //    @LoginRequest
    @DeleteMapping("/community/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommunityComment(@LoginUser User user,
                                       @PathVariable("commentId") Long commentId)
    {
        commentService.delete(user.getId(), commentId);
    }
}
