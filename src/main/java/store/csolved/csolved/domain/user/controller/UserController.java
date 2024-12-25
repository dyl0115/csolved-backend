package store.csolved.csolved.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.csolved.csolved.auth.AuthValidator;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.dto.SignInForm;
import store.csolved.csolved.domain.user.dto.SignUpForm;
import store.csolved.csolved.domain.user.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController
{
    private final AuthValidator authValidator;
    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm,
                         BindingResult signUpErrors,
                         @ModelAttribute("signInForm") SignInForm signInForm)
    {
        // 입력된 두 비밀번호가 같은지, 존재하는 이메일인지, 존재하는 닉네임인지 검사.
        authValidator.checkSignUpForm(signUpForm, signUpErrors);

        if (signUpErrors.hasErrors())
        {
            return "auth";
        }
        else
        {
            userService.signUp(signUpForm);
            return "redirect:/auth";
        }
    }

    @DeleteMapping("/withdraw")
    public String withdraw(@LoginUser User user,
                           @ModelAttribute("signUpForm") SignUpForm signUpForm,
                           @ModelAttribute("signInForm") SignInForm signInForm)
    {
        userService.withdraw(user);
        return "auth";
    }
}
