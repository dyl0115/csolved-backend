package store.csolved.csolved.domain.auth.service.command;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.user.User;

@Getter
@Builder
public class SignupCommand
{
    String email;
    String nickname;
    String password;

    public User toEntity(String hashedPassword)
    {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(hashedPassword)
                .build();
    }
}
