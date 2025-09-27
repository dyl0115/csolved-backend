package store.csolved.csolved.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.auth.service.command.SigninCommand;
import store.csolved.csolved.domain.auth.service.command.SignupCommand;
import store.csolved.csolved.domain.user.mapper.entity.User;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.global.utils.PasswordManager;
import store.csolved.csolved.global.utils.AuthSessionManager;


@RequiredArgsConstructor
@Component
public class AuthService
{
    private final AuthSessionManager authSession;
    private final PasswordManager passwordManager;
    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignupCommand command)
    {
        if (userMapper.existsByEmail(command.getEmail()))
        {
            throw new CsolvedException(ExceptionCode.DUPLICATE_EMAIL);
        }

        if (userMapper.existsByNickname(command.getNickname()))
        {
            throw new CsolvedException(ExceptionCode.DUPLICATE_NICKNAME);
        }

        String hashedPassword = passwordManager.hashPassword(command.getPassword());
        userMapper.insertUser(command.toEntity(hashedPassword));
    }

    public User signIn(SigninCommand command)
    {
        User user = userMapper.findUserByEmail(command.getEmail());

        if (user == null)
        {
            throw new CsolvedException(ExceptionCode.USER_NOT_FOUND);
        }

        String storedPassword = userMapper.findPasswordByEmail(command.getEmail());

        if (storedPassword == null || !passwordManager.verifyPassword(command.getPassword(), storedPassword))
        {
            throw new CsolvedException(ExceptionCode.INVALID_PASSWORD);
        }

        authSession.setLoginUser(user);

        return user;
    }

    public User checkSession()
    {
        User principal = authSession.getLoginUser();

        if (principal == null)
        {
            throw new CsolvedException(ExceptionCode.INVALID_SESSION);
        }

        return principal;
    }

    public void signOut()
    {
        User principal = authSession.getLoginUser();

        if (principal == null)
        {
            throw new CsolvedException(ExceptionCode.INVALID_SESSION);
        }

        authSession.invalidateSession();
    }

    @Transactional
    public void withdraw(User user)
    {
        User principal = authSession.getLoginUser();

        if (principal == null)
        {
            throw new CsolvedException(ExceptionCode.INVALID_SESSION);
        }

        authSession.invalidateSession();
        userMapper.delete(user.getId());
    }
}
