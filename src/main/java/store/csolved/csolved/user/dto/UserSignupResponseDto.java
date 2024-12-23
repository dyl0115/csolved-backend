package store.csolved.csolved.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.csolved.csolved.user.User;

@Getter
@NoArgsConstructor
public class UserSignupResponseDto
{
    private Long id;
    private String nickname;

    public static UserSignupResponseDto from(User user)
    {
        UserSignupResponseDto dto = new UserSignupResponseDto();
        dto.id = user.getId();
        dto.nickname = user.getNickname();
        return dto;
    }
}
