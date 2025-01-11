package store.csolved.csolved.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.auth.dto.SignInForm;
import store.csolved.csolved.auth.dto.SignUpForm;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordUtils;

@RequiredArgsConstructor
@Component
public class AuthService
{
    public static final String LOGIN_USER_SESSION_KEY = "loginUser";

    private final HttpSession httpSession;

    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignUpForm form)
    {
        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.hashPassword(form.getPassword());
        form.setPassword(hashedPassword);

        userMapper.insertUser(form.toUser());
    }

    public void signIn(SignInForm form)
    {
        User loginUser = userMapper.findUserByEmail(form.getEmail());
        httpSession.setAttribute(LOGIN_USER_SESSION_KEY, loginUser);
    }

    public void signOut()
    {
        httpSession.removeAttribute(LOGIN_USER_SESSION_KEY);
    }

    @Transactional
    public void withdraw(User user)
    {
        httpSession.removeAttribute(LOGIN_USER_SESSION_KEY);
        userMapper.deleteUserById(user.getId());
    }

    public void checkSignUpValid(SignUpForm form, BindingResult result)
    {
        checkEmailDuplicate(form, result);

        checkNicknameDuplicate(form, result);

        checkPasswordMatch(form, result);
    }

    public void checkUserExist(SignInForm form, BindingResult signInErrors)
    {
        String storedPassword = userMapper.findPasswordByEmail(form.getEmail());
        if (storedPassword == null || !PasswordUtils.verifyPassword(form.getPassword(), storedPassword))
        {
            signInErrors.reject("notExist");
        }
    }

    private void checkEmailDuplicate(SignUpForm form, BindingResult errors)
    {
        if (userMapper.existsByEmail(form.getEmail()))
        {
            errors.rejectValue("email", "duplicate");
        }
    }

    private void checkNicknameDuplicate(SignUpForm form, BindingResult errors)
    {
        if (userMapper.existsByNickname(form.getNickname()))
        {
            errors.rejectValue("nickname", "duplicate");
        }
    }

    private void checkPasswordMatch(SignUpForm form, BindingResult errors)
    {
        if (!form.getPassword().equals(form.getPasswordConfirm()))
        {
            errors.rejectValue("passwordConfirm", "notMatch");
        }
    }
}
