package store.csolved.csolved.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.csolved.csolved.user.dto.UserSignInRequest;
import store.csolved.csolved.user.dto.UserSignUpRequest;
import store.csolved.csolved.user.dto.UserInfo;
import store.csolved.csolved.user.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController
{
    private final UserService userService;

    @GetMapping("/users/auth")
    public String authForm()
    {
        return "authform";
    }

    @PostMapping("/users/signup")
    public String signUp(@ModelAttribute UserSignUpRequest request)
    {
        userService.signUp(request);
        return "redirect:/users/auth";
    }

    @PostMapping("/users/signin")
    public String signIn(HttpSession session, @ModelAttribute UserSignInRequest request)
    {
        UserInfo user = userService.signIn(request);
        session.setAttribute("user", user);
        return "redirect:/questions/";
    }
}
