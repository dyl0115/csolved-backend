package store.csolved.csolved.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.csolved.csolved.user.dto.UserSignupRequestDto;
import store.csolved.csolved.user.dto.UserSignupResponseDto;
import store.csolved.csolved.user.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController
{
    private final UserService userService;

    @PostMapping("/users/signup")
    public UserSignupResponseDto signUp(@RequestBody UserSignupRequestDto dto)
    {
        return userService.signUp(dto);
    }
}
