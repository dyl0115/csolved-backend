package store.csolved.csolved.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.controller.dto.request.SignupRequest;
import store.csolved.csolved.domain.auth.controller.dto.response.SignupResponse;
import store.csolved.csolved.domain.auth.controller.dto.response.WithdrawResponse;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.domain.auth.service.AuthService;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.auth.controller.form.SignInForm;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/signUp")
    public SignupResponse processSignup(@Valid @RequestBody SignupRequest request)
    {
        authService.signup(request.toCommand());
        return SignupResponse.success();
    }

    @LoginRequest
    @DeleteMapping("/withdraw")
    public WithdrawResponse processWithdraw(@LoginUser User user)
    {
        authService.withdraw(user);
        return WithdrawResponse.success();
    }

//    @LoginRequest
//    @PostMapping("/signOut")
//    public String processSignOut()
//    {
//        authService.signOut();
//        return null;
//    }
//
//    @PostMapping("/signIn")
//    public String processSignIn(@Valid @ModelAttribute("signInForm") SignInForm signInForm,
//                                BindingResult result)
//    {
//        if (result.hasErrors())
//        {
//            return null;
//        }
//
//        authService.signIn(signInForm);
//        return null;
//    }
}