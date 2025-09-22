package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.notice.service.NoticeFacade;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.answer.controller.form.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.controller.form.CommentCreateForm;
import store.csolved.csolved.domain.code_review.service.CodeReviewFacade;
import store.csolved.csolved.domain.community.service.CommunityFacade;
import store.csolved.csolved.domain.question.service.QuestionFacade;
import store.csolved.csolved.utils.login.LoginUser;

import static store.csolved.csolved.domain.code_review.controller.CodeReviewController.VIEWS_CODE_REVIEW_DETAIL;
import static store.csolved.csolved.domain.notice.controller.NoticeController.VIEWS_NOTICE_DETAIL;
import static store.csolved.csolved.domain.question.controller.QuestionController.VIEWS_QUESTION_DETAIL;

@RequiredArgsConstructor
@Controller
public class AnswerController
{
    private final NoticeFacade noticeFacade;
    private final QuestionFacade questionFacade;
    private final AnswerService answerService;
    private final CommunityFacade communityFacade;
    private final CodeReviewFacade codeReviewFacade;

    @LoginRequest
    @PostMapping("/notice/{postId}/answers")
    public String saveNoticeAnswer(@PathVariable Long postId,
                                   @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                   BindingResult result,
                                   Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("noticeDetails", noticeFacade.getNotice(postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_NOTICE_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/notice/" + postId + "/read";
    }

    @LoginRequest
    @PostMapping("/question/{postId}/answers")
    public String saveQuestionAnswer(@LoginUser User user,
                                     @PathVariable Long postId,
                                     @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                     BindingResult result,
                                     Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.viewQuestion(user.getId(), postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_QUESTION_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/question/" + postId + "/read";
    }

    @LoginRequest
    @PostMapping("/community/{postId}/answers")
    public String saveCommunityAnswer(@LoginUser User user,
                                      @PathVariable Long postId,
                                      @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                      BindingResult result,
                                      Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("communityPostDetails", communityFacade.getPost(user.getId(), postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return null;
        }

        answerService.saveAnswer(form.toAnswer());
        model.addAttribute("communityPostDetails", communityFacade.getPost(user.getId(), postId));
        model.addAttribute("commentCreateForm", CommentCreateForm.empty());
        return "redirect:/community/" + postId + "/read";
    }

    @LoginRequest
    @PostMapping("/code-review/{postId}/answers")
    public String saveCodeReviewAnswer(@LoginUser User user,
                                       @PathVariable Long postId,
                                       @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                       BindingResult result,
                                       Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("codeReviewDetails", codeReviewFacade.viewCodeReview(user.getId(), postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_CODE_REVIEW_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/code-review/" + postId + "/read";
    }
}