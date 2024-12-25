package store.csolved.csolved.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.user.User;
import store.csolved.csolved.user.dto.UserSignInForm;
import store.csolved.csolved.user.dto.SignUpForm;
import store.csolved.csolved.user.dto.UserInfo;
import store.csolved.csolved.user.exceptions.*;
import store.csolved.csolved.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordUtils;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserMapper userMapper;

    @Transactional
    public UserInfo signUp(SignUpForm form)
    {
        // 입력 검증 및 유효성 검사
        SignUpFailedException ex = new SignUpFailedException();
        if (userMapper.existsByEmail(form.getEmail()))
        {
            ex.addError("email", "duplicate");
        }
        if (userMapper.existsByNickname(form.getNickname()))
        {
            ex.addError("nickname", "duplicate");
        }
        if (!ex.getErrors().isEmpty())
        {
            throw ex;
        }

        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.hashPassword(form.getPassword());
        User user = SignUpForm.toEntity(form);
        user.updatePassword(hashedPassword);

        // 데이터베이스에 저장
        userMapper.insertUser(user);

        // 결과 반환
        return UserInfo.from(user);
    }

    public UserInfo signIn(UserSignInForm request)
    {
        validateAuthentication(request);

        User user = userMapper.findUserByEmail(request.getEmail());

        return UserInfo.from(user);
    }

    private void validateAuthentication(UserSignInForm request)
    {
        String storedPassword = userMapper.findPasswordByEmail(request.getEmail());
        if (storedPassword == null || !PasswordUtils.verifyPassword(request.getPassword(), storedPassword))
        {
            throw new AuthenticationFailedException();
        }
    }
}
