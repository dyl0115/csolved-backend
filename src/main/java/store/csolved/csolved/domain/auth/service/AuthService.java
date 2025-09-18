package store.csolved.csolved.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.auth.exception.DuplicateEmailException;
import store.csolved.csolved.domain.auth.exception.DuplicateNicknameException;
import store.csolved.csolved.domain.auth.service.dto.SignupCommand;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordManager;
import store.csolved.csolved.utils.AuthSessionManager;


@RequiredArgsConstructor
@Component
public class AuthService
{
    private final AuthSessionManager authSession;
    private final PasswordManager passwordManager;
    private final UserMapper userMapper;

    @Transactional
    public void signup(SignupCommand command)
    {
        if (userMapper.existsByEmail(command.getEmail()))
        {
            throw new DuplicateEmailException();
        }

        if (userMapper.existsByNickname(command.getNickname()))
        {
            throw new DuplicateNicknameException();
        }

        String hashedPassword = passwordManager.hashPassword(command.getPassword());
        userMapper.insertUser(command.toEntity(hashedPassword));
    }

//    public void signIn(SignInForm form)
//    {
//        User loginUser = userMapper.findUserByEmail(form.getEmail());
//        authSession.setLoginUser(loginUser);
//    }
//
//    public void signOut()
//    {
//        authSession.invalidateSession();
//    }

    @Transactional
    public void withdraw(User user)
    {
        authSession.invalidateSession();
        userMapper.delete(user.getId());
    }
}
