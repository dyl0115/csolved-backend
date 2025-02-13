package store.csolved.csolved.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.auth.controller.form.SignInForm;
import store.csolved.csolved.domain.auth.controller.form.SignUpForm;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordManager;
import store.csolved.csolved.utils.SessionManager;

@RequiredArgsConstructor
@Component
public class AuthService
{
    private final SessionManager sessionManager;
    private final PasswordManager passwordManager;
    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignUpForm form)
    {
        String hashedPassword = passwordManager.hashPassword(form.getPassword());
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
}
