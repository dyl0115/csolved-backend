package store.csolved.csolved.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.csolved.csolved.user.User;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class UserInfo
{
    private final Long id;
    private final String email;
    private final String nickname;
    private final String company;
    private final Boolean isAdmin;
    private final LocalDateTime createdAt;

    public static UserInfo from(User user)
    {
        return new UserInfo(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getCompany(),
                user.getIsAdmin(),
                user.getCreatedAt());
    }
}
