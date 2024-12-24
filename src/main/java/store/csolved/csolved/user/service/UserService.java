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

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserMapper userMapper;

    @Transactional
    public UserInfo signUp(UserSignUpRequest request)
    {
        User user = UserSignUpRequest.toEntity(request);

        validateEmailDuplication(user.getEmail());
        validateNicknameDuplication(user.getNickname());

        userMapper.insertUser(user);

        return UserInfo.from(user);
    }

    public UserInfo signIn(UserSignInRequest request)
    {
        User user = userMapper.findUserByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null)
        {
            throw new AuthenticationFailedException();
        }
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
