package store.csolved.csolved.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.notice.controller.NoticeController;
import store.csolved.csolved.domain.notice.service.NoticeFacade;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.answer.controller.form.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.form.CommentCreateForm;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.code_review.controller.CodeReviewController;
import store.csolved.csolved.domain.community.controller.CommunityController;
import store.csolved.csolved.domain.question.controller.QuestionController;
import store.csolved.csolved.domain.code_review.service.CodeReviewFacade;
import store.csolved.csolved.domain.community.service.CommunityFacade;
import store.csolved.csolved.domain.question.service.QuestionFacade;
import store.csolved.csolved.utils.login.LoginUser;

import static store.csolved.csolved.domain.notice.controller.NoticeController.*;

@RequiredArgsConstructor
@Controller
public class CommentController
{
    private final CommentService commentService;
    private final NoticeFacade noticeFacade;
    private final QuestionFacade questionFacade;
    private final CommunityFacade communityFacade;
    private final CodeReviewFacade codeReviewFacade;

    // TODO: 여기 싹 수정해야함
    @LoginRequest
    @PostMapping("/notice/{postId}/answers/{answerId}/comment")
    public String saveNoticeComment(@PathVariable("postId") Long postId,
                                    @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                                    BindingResult result,
                                    Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("noticeDetails", noticeFacade.getNotice(postId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            model.addAttribute("commentCreateFrom", form);
            return VIEWS_NOTICE_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/notice/" + postId;
    }

    @LoginRequest
    @PostMapping("/community/{postId}/answers/{answerId}/comment")
    public String saveCommunityComment(@LoginUser User user,
                                       @PathVariable("postId") Long postId,
                                       @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                                       BindingResult result,
                                       Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("communityPostDetails", communityFacade.getCommunityPost(user.getId(), postId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            return CommunityController.VIEWS_COMMUNITY_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/community/" + postId;
    }


    @LoginRequest
    @PostMapping("/questions/{postId}/answers/{answerId}/comment")
    public String saveQuestionComment(@LoginUser User user,
                                      @PathVariable("postId") Long postId,
                                      @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                                      BindingResult result,
                                      Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.getQuestion(user.getId(), postId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            model.addAttribute("commentCreateFrom", form);
            return QuestionController.VIEWS_QUESTION_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/question/" + postId;
    }

    @LoginRequest
    @PostMapping("/code-review/{postId}/answers/{answerId}/comment")
    public String saveCodeReviewComment(@LoginUser User user,
                                        @PathVariable("postId") Long postId,
                                        @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                                        BindingResult result,
                                        Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", codeReviewFacade.getCodeReview(user.getId(), postId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            model.addAttribute("commentCreateFrom", form);
            return CodeReviewController.VIEWS_CODE_REVIEW_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/code-review/" + postId;
    }
}
