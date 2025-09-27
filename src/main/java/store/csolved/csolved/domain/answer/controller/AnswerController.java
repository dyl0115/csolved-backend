package store.csolved.csolved.domain.answer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.answer.controller.request.AnswerCreateRequest;
import store.csolved.csolved.domain.answer.controller.response.AnswerWithCommentsResponse;
import store.csolved.csolved.domain.answer.service.AnswerCommandService;
import store.csolved.csolved.domain.answer.service.AnswerQueryService;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.utils.login.LoginUser;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class AnswerController
{
    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;

    @PostMapping("/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@LoginUser User user,
                     @Valid @RequestBody AnswerCreateRequest request)
    {
        answerCommandService.save(user.getId(), AnswerCreateCommand.from(request));
    }

    @DeleteMapping("/answer/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@LoginUser User user,
                       @PathVariable Long answerId)
    {
        answerCommandService.delete(user.getId(), answerId);
    }

    @GetMapping("/{postId}/answers")
    public List<AnswerWithCommentsResponse> getAnswersWithComments(@PathVariable Long postId)
    {
        return AnswerWithCommentsResponse.from(answerQueryService.getAnswersWithComments(postId));
    }
}