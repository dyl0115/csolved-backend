package store.csolved.csolved.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.service.AuthService;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.auth.dto.SignInForm;
import store.csolved.csolved.auth.dto.SignUpForm;

import static store.csolved.csolved.auth.AuthConstants.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(LOGIN_PAGE_URL)
public class AuthController
{
    private final AuthService authService;

    @GetMapping
    public String authForm(@ModelAttribute("signInForm") SignInForm signInForm,
                           @ModelAttribute("signUpForm") SignUpForm signUpForm)
    {
        return LOGIN_PAGE_URL;
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm,
                         BindingResult result,
                         @ModelAttribute("signInForm") SignInForm signInForm)
    {
        authService.checkSignUpValid(signUpForm, result);
        if (result.hasErrors()) return LOGIN_PAGE_URL;

        authService.signUp(signUpForm);
        return REDIRECT_LOGIN_PAGE_URL;
    }

    @PostMapping("/signin")
    public String signIn(HttpSession session,
                         @ModelAttribute("signUpForm") SignUpForm signUpForm,
                         @Valid @ModelAttribute("signInForm") SignInForm signInForm,
                         BindingResult result)
    {
        authService.checkUserExist(signInForm, result);
        if (result.hasErrors()) return LOGIN_PAGE_URL;

        authService.signIn(session, signInForm);

        return "redirect:/questions?page=1";
    }

    @GetMapping("/signout")
    public String signOut(@LoginUser User user,
                          HttpSession session)
    {
        authService.signOut(session);

        return REDIRECT_LOGIN_PAGE_URL;
    }

    @DeleteMapping("/withdraw")
    public String withdraw(@LoginUser User user,
                           @ModelAttribute("signUpForm") SignUpForm signUpForm,
                           @ModelAttribute("signInForm") SignInForm signInForm)
    {
        authService.withdraw(user);
        return LOGIN_PAGE_URL;
    }
}
