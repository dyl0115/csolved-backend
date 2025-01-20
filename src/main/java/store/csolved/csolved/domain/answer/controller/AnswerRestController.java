package store.csolved.csolved.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.answer.controller.dto.AnswerScoreResponse;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/api/answers")
@RequiredArgsConstructor
@RestController
public class AnswerRestController
{
    private final AnswerService answerService;

    @LoginRequest
    @PostMapping("/{answerId}/score")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerScoreResponse score(@LoginUser User user,
                                     @PathVariable Long answerId,
                                     @RequestBody Long score)
    {
        if (answerService.hasAlreadyScored(answerId, user.getId()))
        {
            throw new AlreadyScoredException("이미 평가한 답변입니다.");
        }

        Answer answer = answerService.score(answerId, user.getId(), score);
        return AnswerScoreResponse.from(answer);
    }

    @LoginRequest
    @DeleteMapping("/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long answerId)
    {
        answerService.delete(answerId);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class AlreadyScoredException extends RuntimeException
    {
        public AlreadyScoredException(String message)
        {
            super(message);
        }
    }
}
