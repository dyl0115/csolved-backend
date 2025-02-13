package store.csolved.csolved.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity
{
    private String profileImage;
    private String email;
    private String password;
    private String nickname;
    private String company;
    private Boolean admin;

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
