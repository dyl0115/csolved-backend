package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
import static store.csolved.csolved.domain.community.controller.CommunityController.VIEWS_COMMUNITY_DETAIL;
import static store.csolved.csolved.domain.question.controller.QuestionController.VIEWS_QUESTION_DETAIL;

@RequiredArgsConstructor
@Controller
public class AnswerController
{
    private final QuestionFacade questionFacade;
    private final AnswerService answerService;
    private final CommunityFacade communityFacade;
    private final CodeReviewFacade codeReviewFacade;

    @LoginRequest
    @PostMapping("/questions/{postId}/answers")
    public String saveQuestionAnswers(@PathVariable Long postId,
                                      @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                      BindingResult result,
                                      Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.getQuestion(postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_QUESTION_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/question/" + postId;
    }

    @LoginRequest
    @PostMapping("/community/{postId}/answers")
    public String saveCommunityAnswers(@LoginUser User user,
                                       @PathVariable Long postId,
                                       @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                       BindingResult result,
                                       Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("communityPostDetails", communityFacade.getCommunityPost(user.getId(), postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_COMMUNITY_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/community/" + postId;
    }

    @LoginRequest
    @PostMapping("/code-review/{postId}/answers")
    public String saveCodeReviewAnswers(@PathVariable Long postId,
                                        @Valid @ModelAttribute("answerCreateForm") AnswerCreateForm form,
                                        BindingResult result,
                                        Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("codeReviewDetails", codeReviewFacade.getCodeReview(postId));
            model.addAttribute("commentCreateForm", CommentCreateForm.empty());
            return VIEWS_CODE_REVIEW_DETAIL;
        }
        answerService.saveAnswer(form.toAnswer());
        return "redirect:/code-review/" + postId;
    }
}