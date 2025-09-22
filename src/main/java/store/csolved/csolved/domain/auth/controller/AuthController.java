package store.csolved.csolved.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.controller.dto.request.SigninRequest;
import store.csolved.csolved.domain.auth.controller.dto.request.SignupRequest;
import store.csolved.csolved.domain.auth.controller.dto.response.*;
import store.csolved.csolved.domain.auth.service.AuthService;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/signUp")
    public SignUpResponse processSignup(@Valid @RequestBody SignupRequest request)
    {
        authService.signUp(request.toCommand());
        return SignUpResponse.success();
    }

    @PostMapping("/signIn")
    public SignInResponse processSignIn(@Valid @RequestBody SigninRequest request)
    {
        User principal = authService.signIn(request.toCommand());
        return SignInResponse.success(principal);
    }

    @DeleteMapping("/signOut")
    public SignOutResponse processSignOut()
    {
        authService.signOut();
        return SignOutResponse.success();
    }

    @GetMapping("/session")
    public CheckSessionResponse checkSession()
    {
        User principal = authService.checkSession();
        return CheckSessionResponse.success(principal);
    }

    @DeleteMapping("/withdraw")
    public WithdrawResponse processWithdraw(@LoginUser User user)
    {
        authService.withdraw(user);
        return WithdrawResponse.success();
    }
}