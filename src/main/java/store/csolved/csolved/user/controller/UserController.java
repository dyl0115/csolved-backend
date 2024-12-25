package store.csolved.csolved.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.csolved.csolved.user.User;
import store.csolved.csolved.user.dto.SignInForm;
import store.csolved.csolved.user.dto.SignUpForm;
import store.csolved.csolved.user.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController
{
    private final AuthValidator authValidator;
    private final UserService userService;

    @GetMapping("/users/auth")
    public String authForm(Model model)
    {
        model.addAttribute("signUpForm", new SignUpForm());
        return "auth";
    }

    @PostMapping("/users/signup")
    public String signUp(@Valid @ModelAttribute SignUpForm form, BindingResult errors)
    {
        // 입력된 두 비밀번호가 같은지, 존재하는 이메일인지, 존재하는 닉네임인지 검사.
        authValidator.checkSignUpForm(form, errors);

        if (errors.hasErrors())
        {
            return "auth";
        }
        else
        {
            userService.signUp(form);
            return "redirect:/users/auth";
        }
    }

    @PostMapping("/users/signin")
    public String signIn(HttpSession session, @Valid @ModelAttribute SignInForm form, BindingResult errors)
    {
        // 존재하는 회원인지, 비밀번호가 올바른지 검사.
        authValidator.checkUserExist(form, errors);

        if (errors.hasErrors())
        {
            return "auth";
        }
        else
        {
            User user = userService.signIn(form);
            session.setAttribute("user", user);
            return "redirect:/questions";
        }
    }
}
