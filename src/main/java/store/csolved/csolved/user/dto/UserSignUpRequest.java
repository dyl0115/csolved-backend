package store.csolved.csolved.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.csolved.csolved.user.User;

@Setter
@Getter
@NoArgsConstructor
public class UserSignUpRequest
{
    private String email;
    private String nickname;
    private String password;

    public static User toEntity(UserSignUpRequest request)
    {
        return User.create(
                request.getEmail(),
                request.getPassword(),
                request.getNickname());
    }
}
