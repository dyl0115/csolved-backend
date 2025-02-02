package store.csolved.csolved.domain.post.controller.question;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.post.facade.question.QuestionFacade;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/api/questions")
@RequiredArgsConstructor
@RestController
public class QuestionRestController
{
    private final QuestionFacade questionFacade;

    @LoginRequest
    @PostMapping("/{questionId}/likes")
    public ResponseEntity<Void> addLike(@LoginUser User user,
                                        @PathVariable Long questionId)
    {
        boolean valid = questionFacade.addLike(questionId, user.getId());
        if (!valid)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequest
    @DeleteMapping("/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long questionId)
    {
        questionFacade.delete(questionId);
    }
}
