package store.csolved.csolved.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.csolved.csolved.user.User;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto
{
    private String email;
    private String password;
    private String nickname;
    private String company;

    public static User toEntity(UserSignupRequestDto dto)
    {
        return User.create(
                dto.getEmail(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getCompany(),
                false);
    }
}
