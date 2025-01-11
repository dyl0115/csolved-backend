package store.csolved.csolved.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.annotation.LoginRequest;
import store.csolved.csolved.auth.service.AuthService;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.auth.dto.SignInForm;
import store.csolved.csolved.auth.dto.SignUpForm;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController
{
    public final static String VIEWS_AUTH_FORM = "/auth";

    private final AuthService authService;

    @ModelAttribute("signInForm")
    public SignInForm initSignInForm()
    {
        return new SignInForm();
    }

    @ModelAttribute("signUpForm")
    public SignUpForm initSignUpForm()
    {
        return new SignUpForm();
    }

    @GetMapping
    public String initAuthForm()
    {
        return VIEWS_AUTH_FORM;
    }

    @PostMapping("/signup")
    public String processSignUp(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm,
                                BindingResult result)
    {
        authService.checkSignUpValid(signUpForm, result);

        if (result.hasErrors())
        {
            return VIEWS_AUTH_FORM;
        }

        authService.signUp(signUpForm);
        return "redirect:/auth";
    }

    @PostMapping("/signin")
    public String processSignIn(@Valid @ModelAttribute("signInForm") SignInForm signInForm,
                                BindingResult result)
    {
        authService.checkUserExist(signInForm, result);

        if (result.hasErrors())
        {
            return VIEWS_AUTH_FORM;
        }

        authService.signIn(signInForm);
        return "redirect:/questions?page=1";
    }

    @LoginRequest
    @GetMapping("/signout")
    public String processSignOut()
    {
        authService.signOut();
        return "redirect:/auth";
    }

    @LoginRequest
    @DeleteMapping("/withdraw")
    public String processWithdraw(@LoginUser User user)
    {
        authService.withdraw(user);
        return "redirect:/auth";
    }
}