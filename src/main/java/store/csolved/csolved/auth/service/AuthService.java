package store.csolved.csolved.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.auth.controller.dto.SignInForm;
import store.csolved.csolved.auth.controller.dto.SignUpForm;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordUtils;
import store.csolved.csolved.utils.SessionManager;

@RequiredArgsConstructor
@Component
public class AuthService
{
    private final SessionManager sessionManager;
    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignUpForm form)
    {
        String hashedPassword = PasswordUtils.hashPassword(form.getPassword());
        userMapper.insertUser(SignUpForm.createEncodedUser(form, hashedPassword));
    }

    public void signIn(SignInForm form)
    {
        User loginUser = userMapper.findUserByEmail(form.getEmail());
        sessionManager.setLoginUser(loginUser);
    }

    public void signOut()
    {
        sessionManager.invalidateSession();
    }

    @Transactional
    public void withdraw(User user)
    {
        sessionManager.invalidateSession();
        userMapper.delete(user.getId());
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
