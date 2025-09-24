package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;
import store.csolved.csolved.domain.answer.service.AnswerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community/answer")
public class AnswerController
{
    private final AnswerService answerService;

    @LoginRequest
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AnswerCreateRequest request)
    {
        answerService.save(request.toCommand());
    }

    @LoginRequest
    @DeleteMapping("/{answerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long answerId)
    {
        answerService.delete(answerId);
    }

}