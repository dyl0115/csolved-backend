package store.csolved.csolved.domain.code_review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.code_review.service.CodeReviewFacade;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/api/code-review")
@RequiredArgsConstructor
@RestController
public class CodeReviewRestController
{
    private final CodeReviewFacade codeReviewFacade;

    @LoginRequest
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> addLike(@LoginUser User user,
                                        @PathVariable Long postId)
    {
        boolean valid = codeReviewFacade.addLike(postId, user.getId());
        if (!valid)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequest
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long postId)
    {
        codeReviewFacade.delete(postId);
    }
}
