package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;
import store.csolved.csolved.domain.answer.controller.response.AnswersWithCommentsResponse;
import store.csolved.csolved.domain.answer.service.AnswerCommandService;
import store.csolved.csolved.domain.answer.service.AnswerQueryService;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class AnswerController
{
    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;

    //    @LoginRequest
    @PostMapping("/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AnswerCreateRequest request)
    {
        System.out.println("answer save");
        answerCommandService.save(AnswerCreateCommand.from(request));
    }

    //    @LoginRequest
    @DeleteMapping("/answer/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long answerId)
    {
        answerCommandService.delete(answerId);
    }

    @GetMapping("/{postId}/answers")
    public AnswersWithCommentsResponse getAnswersWithComments(@PathVariable Long postId)
    {
        List<AnswerWithCommentsResult> results = answerQueryService.getAnswersWithComments(postId);
        return AnswersWithCommentsResponse.from(results);
    }
}