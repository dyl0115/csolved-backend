package store.csolved.csolved.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User
{
    private Long id;
    private String profileImage;
    private final String email;
    private final String password;
    private final String nickname;
    private final String company;
    private final Boolean admin;
    private final LocalDateTime createdAt;

    public static User create(String email, String password, String nickname, String company, boolean admin)
    {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .company(company)
                .admin(admin)
                .build();
    }
}
