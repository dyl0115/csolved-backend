package store.csolved.csolved.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.comment.controller.dto.CommentCreateForm;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.post.controller.community.CommunityController;
import store.csolved.csolved.domain.post.controller.question.QuestionController;
import store.csolved.csolved.domain.post.facade.community.CommunityFacade;
import store.csolved.csolved.domain.post.facade.question.QuestionFacade;

@RequiredArgsConstructor
@Controller
public class CommentController
{
    private final CommentService commentService;
    private final QuestionFacade questionFacade;
    private final CommunityFacade communityFacade;

    // TODO: 여기 싹 수정해야함

    @LoginRequest
    @PostMapping("/community/{postId}/answers/{answerId}/comment")
    public String saveCommunityComment(@PathVariable("postId") Long postId,
                                       @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                                       BindingResult result,
                                       Model model)
    {
        if (result.hasErrors())
        {
            System.out.println("에러 목록");
            result.getAllErrors().forEach(System.out::println);
            model.addAttribute("communityPostDetails", communityFacade.getCommunityPost(postId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            return CommunityController.VIEWS_COMMUNITY_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/community/" + postId;
    }


    @LoginRequest
    @PostMapping("/comments")
    public String save(@RequestParam("postId") Long questionId,
                       @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
                       BindingResult result,
                       Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("questionDetails", questionFacade.getQuestion(questionId));
            model.addAttribute("answerCreateForm", AnswerCreateForm.empty());
            model.addAttribute("commentCreateFrom", form);
            return QuestionController.VIEWS_QUESTION_DETAIL;
        }
        commentService.saveComment(form.toComment());
        return "redirect:/questions/" + questionId;
    }
}
