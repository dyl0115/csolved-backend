package store.csolved.csolved.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.user.User;
import store.csolved.csolved.user.dto.UserSignupRequestDto;
import store.csolved.csolved.user.dto.UserSignupResponseDto;
import store.csolved.csolved.user.exceptions.DuplicateEmailException;
import store.csolved.csolved.user.mapper.UserMapper;

import static store.csolved.csolved.user.dto.UserSignupRequestDto.toEntity;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserMapper userMapper;

    public UserSignupResponseDto signUp(UserSignupRequestDto dto)
    {
        User user = toEntity(dto);

        String duplicateEmail = checkEmailDuplicate(user);
        if (duplicateEmail != null)
        {
            throw new DuplicateEmailException();
        }

        // 이메일 인증은 나중에

        userMapper.insertUser(user);
        User userFind = userMapper.findUserById(user.getId());
        return UserSignupResponseDto.from(userFind);
    }

    private String checkEmailDuplicate(User user)
    {
        return userMapper.isEmailDuplicate(user.getEmail());
    }

}
