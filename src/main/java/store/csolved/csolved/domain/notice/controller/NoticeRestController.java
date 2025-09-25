package store.csolved.csolved.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.utils.login.LoginRequest;

@RequestMapping("/api/notice")
@RequiredArgsConstructor
@RestController
public class NoticeRestController
{
//    private final NoticeFacade noticeFacade;

    @LoginRequest
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long postId)
    {
//        noticeFacade.delete(postId);
    }
}
