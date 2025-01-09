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

import static store.csolved.csolved.auth.AuthConstants.*;

@RequiredArgsConstructor
@Component
public class AuthService
{
    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignUpForm form)
    {
        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.hashPassword(form.getPassword());
        form.setPassword(hashedPassword);

        userMapper.insertUser(form.toUser());
    }

    public void signIn(HttpSession session, SignInForm form)
    {
        User loginUser = userMapper.findUserByEmail(form.getEmail());
        session.setAttribute(LOGIN_USER_SESSION_KEY, loginUser);
    }

    public void signOut(HttpSession session)
    {
        session.removeAttribute(LOGIN_USER_SESSION_KEY);
    }

    @Transactional
    public void withdraw(User user)
    {
        userMapper.deleteUserById(user.getId());
    }

    public void isSignUpValid(SignUpForm form, BindingResult errors)
    {
        checkEmailDuplicate(form, errors);

        checkNicknameDuplicate(form, errors);

        checkPasswordMatch(form, errors);
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
