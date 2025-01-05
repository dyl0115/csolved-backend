package store.csolved.csolved.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class AnswerController
{
    private final AnswerService answerService;

    @PostMapping("/questions/{questionId}/answers")
    public String saveAnswer(@LoginUser User user,
                             @PathVariable Long questionId,
                             @ModelAttribute("answerCreateForm") AnswerCreateForm answerCreateForm,
                             BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/article/detail";
        }
        else
        {
            answerService.saveAnswer(user, answerCreateForm);
            return "redirect:/questions/" + questionId;
        }
    }
}
