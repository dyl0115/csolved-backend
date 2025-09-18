package store.csolved.csolved.domain.auth.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.csolved.csolved.domain.auth.controller.dto.response.SignupResponse;
import store.csolved.csolved.domain.auth.exception.DuplicateEmailException;
import store.csolved.csolved.domain.auth.exception.DuplicateNicknameException;
import store.csolved.csolved.domain.auth.service.dto.SignupCommand;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.domain.user.service.UserService;
import store.csolved.csolved.utils.AuthSessionManager;
import store.csolved.csolved.utils.PasswordManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest
{
    @Autowired
    AuthService authService;

    @Autowired
    UserMapper usermapper;

    @BeforeEach
    void setUp()
    {

    }

    SignupCommand createSignupCommand(String email,
                                      String nickname,
                                      String password)
    {
        return SignupCommand.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("정상요청이면 회원가입에 성공해야 한다.")
    void signup()
    {
        //given
        SignupCommand command = createSignupCommand("testEmail@example.com",
                "testNickname",
                "testPassword");

        //when
        authService.signup(command);

        //then
        User foundUser = usermapper.findUserByEmail("testEmail@example.com");
        assertThat(command.getEmail()).isEqualTo(foundUser.getEmail());
        assertThat(command.getNickname()).isEqualTo(foundUser.getNickname());
    }

    @Test
    @DisplayName("이메일 중복 시 DuplicateEmailException이 발생해야 한다.")
    void signup_duplicateEmail_throwException()
    {
        //given
        SignupCommand command1 = createSignupCommand("dupEmail@example.com",
                "david",
                "david123!");

        SignupCommand command2 = createSignupCommand("dupEmail@example.com",
                "marry",
                "marry123!");

        //when then
        authService.signup(command1);
        assertThatThrownBy(() -> authService.signup(command2))
                .isInstanceOf(DuplicateEmailException.class);

        assertThat(usermapper.existsByNickname("david")).isTrue();

        assertThat(usermapper.existsByNickname("marry")).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복 시 DuplicateNicknameException이 발생해야 한다.")
    void signup_DuplicateNickname_ThrowsExceptions()
    {
        //given
        SignupCommand command1 = createSignupCommand("david@example.com",
                "sameNickname",
                "david123!");

        SignupCommand command2 = createSignupCommand("marry@example.com",
                "sameNickname",
                "marry123!");

        //when then
        authService.signup(command1);
        assertThatThrownBy(() -> authService.signup(command2))
                .isInstanceOf(DuplicateNicknameException.class);

        assertThat(usermapper.existsByEmail("david@example.com")).isTrue();

        assertThat(usermapper.existsByNickname("marry@example.com")).isFalse();
    }

    @Test
    void withdraw()
    {

    }
}