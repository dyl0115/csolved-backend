package store.csolved.csolved;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.service.UserService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController
{
    @GetMapping("/test")
    public String testView(Model model)
    {
        User user = User.builder()
                .nickname("double dragon")
                .email("dyl0115@naver.com")
                .profileImage("https://csolved-test-bucket.s3.ap-northeast-2.amazonaws.com/test1/aaaa.webp")
                .build();

        model.addAttribute("user", user);
        return "views/domain/user/update";
    }
}
