package store.csolved.csolved.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.user.User;
import store.csolved.csolved.user.dto.UserSignInRequest;
import store.csolved.csolved.user.dto.UserSignUpRequest;
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
    public UserInfo signUp(UserSignUpRequest request)
    {
        // 입력 검증 및 유효성 검사
        validateEmailDuplication(request.getEmail());
        validateNicknameDuplication(request.getNickname());

        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.hashPassword(request.getPassword());
        User user = UserSignUpRequest.toEntity(request);
        user.updatePassword(hashedPassword);

        // 데이터베이스에 저장
        userMapper.insertUser(user);

        // 결과 반환
        return UserInfo.from(user);
    }

    public UserInfo signIn(UserSignInRequest request)
    {
        String password = userMapper.findPasswordByEmail(request.getEmail());
        if (password == null)
        {
            throw new AuthenticationFailedException();
        }

        boolean matched = PasswordUtils.verifyPassword(request.getPassword(), password);
        if (!matched)
        {
            throw new AuthenticationFailedException();
        }

        User user = userMapper.findUserByEmail(request.getEmail());

        return UserInfo.from(user);
    }

    private void validateEmailDuplication(String email)
    {
        if (userMapper.existsByEmail(email))
        {
            throw new DuplicateEmailException();
        }
    }

    private void validateNicknameDuplication(String nickname)
    {
        if (userMapper.existsByNickname(nickname))
        {
            throw new DuplicateNicknameException();
        }
    }
}
