package store.csolved.csolved.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.dto.SignInForm;
import store.csolved.csolved.domain.user.dto.SignUpForm;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordUtils;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserMapper userMapper;

    @Transactional
    public void signUp(SignUpForm form)
    {
        // 비밀번호 암호화
        hashPassword(form);

        // 데이터베이스에 저장
        userMapper.insertUser(form.toUser());
    }

    public User signIn(SignInForm form)
    {
        return userMapper.findUserByEmail(form.getEmail());
    }

    @Transactional
    public void withdraw(User user)
    {
        userMapper.deleteUserById(user.getId());
    }

    private void hashPassword(SignUpForm form)
    {
        String hashedPassword = PasswordUtils.hashPassword(form.getPassword());
        form.setPassword(hashedPassword);
    }
}
