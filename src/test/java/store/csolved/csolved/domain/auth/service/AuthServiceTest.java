package store.csolved.csolved.domain.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.auth.service.command.SigninCommand;
import store.csolved.csolved.domain.auth.service.command.SignupCommand;
import store.csolved.csolved.domain.user.mapper.entity.User;
import store.csolved.csolved.domain.user.mapper.UserMapper;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AuthServiceTest
{
    @Autowired
    AuthService authService;

    @Autowired
    UserMapper usermapper;

    @BeforeEach
    void beforeEach()
    {
        usermapper.deleteAll();
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

    SigninCommand createSigninCommand(String email, String password)
    {
        return SigninCommand.builder()
                .email(email)
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
        authService.signUp(command);

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
        authService.signUp(command1);
        assertThatThrownBy(() -> authService.signUp(command2))
                .isInstanceOf(CsolvedException.class)
                .hasFieldOrPropertyWithValue("code", ExceptionCode.DUPLICATE_EMAIL);

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
        authService.signUp(command1);
        assertThatThrownBy(() -> authService.signUp(command2))
                .isInstanceOf(CsolvedException.class)
                .hasFieldOrPropertyWithValue("code", ExceptionCode.DUPLICATE_NICKNAME);

        assertThat(usermapper.existsByEmail("david@example.com")).isTrue();

        assertThat(usermapper.existsByNickname("marry@example.com")).isFalse();
    }

    @Test
    @DisplayName("없는 이메일로 로그인하면 UserNotFoundException이 발생한다.")
    void signin_Email_Not_Found()
    {
        //given
        SignupCommand command1 = createSignupCommand("david@naver.com",
                "david",
                "david123!");
        authService.signUp(command1);

        //when then
        SigninCommand command2 = createSigninCommand("nono@naver.com", "marry123!");

        assertThatThrownBy(() -> authService.signIn(command2))
                .isInstanceOf(CsolvedException.class)
                .hasFieldOrPropertyWithValue("code", ExceptionCode.USER_NOT_FOUND);
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인하면 InvalidPasswordException이 발생한다.")
    void signin_with_wrong_password()
    {
        //given
        SignupCommand command1 = createSignupCommand("david@naver.com",
                "david",
                "david123!");
        authService.signUp(command1);

        //when then
        SigninCommand command2 = createSigninCommand("david@naver.com", "wrongpassword");

        assertThatThrownBy(() -> authService.signIn(command2))
                .isInstanceOf(CsolvedException.class)
                .hasFieldOrPropertyWithValue("code", ExceptionCode.INVALID_PASSWORD);
    }

    @Test
    void withdraw()
    {

    }
}