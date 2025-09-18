package store.csolved.csolved.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.auth.controller.dto.request.SigninRequest;
import store.csolved.csolved.domain.auth.controller.dto.request.SignupRequest;
import store.csolved.csolved.domain.auth.controller.dto.response.SigninResponse;
import store.csolved.csolved.domain.auth.controller.dto.response.SignupResponse;
import store.csolved.csolved.domain.auth.controller.dto.response.WithdrawResponse;
import store.csolved.csolved.utils.login.LoginRequest;
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
    public SignupResponse processSignup(@Valid @RequestBody SignupRequest request)
    {
        authService.signup(request.toCommand());
        return SignupResponse.success();
    }

    @PostMapping("/signIn")
    public SigninResponse processSignIn(@Valid @RequestBody SigninRequest request)
    {
        authService.signin(request.toCommand());
        return SigninResponse.success();
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

}