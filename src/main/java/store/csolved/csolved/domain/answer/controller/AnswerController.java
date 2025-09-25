package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/answer")
public class AnswerController
{
    private final AnswerService answerService;

    //    @LoginRequest
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AnswerCreateRequest request)
    {
        answerService.save(AnswerCreateCommand.from(request));
    }

    //    @LoginRequest
    @DeleteMapping("/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long answerId)
    {
        answerService.delete(answerId);
    }


}