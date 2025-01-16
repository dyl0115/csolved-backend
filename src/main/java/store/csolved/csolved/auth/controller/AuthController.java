package store.csolved.csolved.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.auth.service.AuthService;
import store.csolved.csolved.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.auth.controller.dto.SignInForm;
import store.csolved.csolved.auth.controller.dto.SignUpForm;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController
{
    public final static String VIEWS_SIGN_IN_FORM = "/views/auth/signIn";
    public final static String VIEWS_SIGN_UP_FORM = "/views/auth/signUp";

    private final AuthService authService;

    @GetMapping("/signIn")
    public String initSignIn(Model model)
    {
        model.addAttribute("signInForm", SignInForm.empty());
        return VIEWS_SIGN_IN_FORM;
    }

    @PostMapping("/signIn")
    public String processSignIn(@Valid @ModelAttribute("signInForm") SignInForm signInForm,
                                BindingResult result)
    {
        authService.checkUserExist(signInForm, result);

        if (result.hasErrors())
        {
            return VIEWS_SIGN_IN_FORM;
        }

        authService.signIn(signInForm);
        return "redirect:/questions?page=1";
    }

    @GetMapping("/signUp")
    public String initSignUp(Model model)
    {
        model.addAttribute("signUpForm", SignUpForm.empty());
        return VIEWS_SIGN_UP_FORM;
    }

    @PostMapping("/signUp")
    public String processSignUp(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm,
                                BindingResult result)
    {
        authService.checkSignUpValid(signUpForm, result);

        if (result.hasErrors())
        {
            return VIEWS_SIGN_UP_FORM;
        }

        authService.signUp(signUpForm);
        return "redirect:/auth/signIn";
    }


    @LoginRequest
    @GetMapping("/signOut")
    public String processSignOut()
    {
        authService.signOut();
        return "redirect:/auth/signIn";
    }

    @LoginRequest
    @DeleteMapping("/withdraw")
    public String processWithdraw(@LoginUser User user)
    {
        authService.withdraw(user);
        return "redirect:/auth/signIn";
    }
}