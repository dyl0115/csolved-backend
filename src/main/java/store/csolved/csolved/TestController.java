package store.csolved.csolved;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.mapper.UserMapper;

@Controller
public class TestController
{
    @GetMapping("/view")
    public String testView(Model model)
    {
        User user = User.create("dyl0115@naver.com",
                "endyd132!!",
                "Test User",
                "company",
                false);

        model.addAttribute("user", user);
        return "/test-view";
    }
}
