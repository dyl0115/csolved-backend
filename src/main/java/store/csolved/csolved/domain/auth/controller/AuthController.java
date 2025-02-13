package store.csolved.csolved.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.auth.service.AuthService;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.validator.SignInFormValidator;
import store.csolved.csolved.validator.SignUpFormValidator;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.auth.controller.form.SignInForm;
import store.csolved.csolved.domain.auth.controller.form.SignUpForm;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController
{
    public final static String VIEWS_SIGN_IN_FORM = "/views/auth/signIn";
    public final static String VIEWS_SIGN_UP_FORM = "/views/auth/signUp";

    private final SignUpFormValidator signUpFormValidator;
    private final SignInFormValidator signInFormValidator;

    private final AuthService authService;

    @InitBinder("signUpForm")
    public void initSignUpBinder(WebDataBinder binder)
    {
        binder.addValidators(signUpFormValidator);
    }

    @InitBinder("signInForm")
    public void initSignInBinder(WebDataBinder binder)
    {
        binder.addValidators(signInFormValidator);
    }

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
        if (result.hasErrors())
        {
            return VIEWS_SIGN_IN_FORM;
        }

        authService.signIn(signInForm);
        return "redirect:/communities?page=1";
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
        if (result.hasErrors())
        {
            return VIEWS_SIGN_UP_FORM;
        }

        authService.signUp(signUpForm);
        return "redirect:/auth/signIn";
    }


    @LoginRequest
    @PostMapping("/signOut")
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