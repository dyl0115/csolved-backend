package store.csolved.csolved.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.user.User;

import java.util.HashMap;
import java.util.Map;

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
            return "questions/detail";
        }
        else
        {
            answerService.saveAnswer(user, answerCreateForm);
            return "redirect:/questions/" + questionId;
        }
    }

    @PostMapping("/api/answers/{answerId}/rate")
    public ResponseEntity<?> rateAnswer(@LoginUser User user,
                                        @PathVariable Long answerId,
                                        @RequestBody Integer rating)
    {
        if (answerService.hasAlreadyRated(answerId, user.getId()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else
        {
            answerService.rateAnswer(answerId, user.getId(), rating);

            Map<String, Object> response = new HashMap<>();
            response.put("averageScore", answerService.findAverageScore(answerId));
            response.put("voterCount", answerService.findVoterCount(answerId));
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/api/answers/{answerId}")
    @ResponseBody
    public String deleteAnswer(@LoginUser User user,
                               @PathVariable Long answerId)
    {
        answerService.deleteAnswer(answerId);
        return "ok";
    }
}
