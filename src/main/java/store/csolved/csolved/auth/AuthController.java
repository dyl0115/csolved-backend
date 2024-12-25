package store.csolved.csolved.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.dto.SignInForm;
import store.csolved.csolved.domain.user.dto.SignUpForm;
import store.csolved.csolved.domain.user.service.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController
{
    private final AuthValidator authValidator;
    private final UserService userService;

    @GetMapping
    public String authForm(@ModelAttribute("signUpForm") SignUpForm signUpForm,
                           @ModelAttribute("signInForm") SignInForm signInForm)
    {
        return "auth";
    }

    @PostMapping("/signin")
    public String signIn(HttpSession session,
                         @ModelAttribute("signUpForm") SignUpForm signUpForm,
                         @Valid @ModelAttribute("signInForm") SignInForm signInForm,
                         BindingResult signInErrors)
    {
        // 존재하는 회원인지, 비밀번호가 올바른지 검사.
        authValidator.checkUserExist(signInForm, signInErrors);

        if (signInErrors.hasErrors())
        {
            return "auth";
        }
        else
        {
            User principal = userService.signIn(signInForm);
            session.setAttribute("principal", principal);
            return "redirect:/questions";
        }
    }

    @GetMapping("/signout")
    public String signOut(@LoginUser User user, HttpSession session)
    {
        session.removeAttribute("principal");
        return "redirect:/auth";
    }
}
