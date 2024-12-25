package store.csolved.csolved.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class User
{
    private Long id;

    private final String email;

    private final String password;

    private final String nickname;

    private final String company;

    private final Boolean isAdmin;

    private final LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public static User create(String email, String password, String nickname, String company, boolean isAdmin)
    {
        return new User(null, email, password, nickname, company, isAdmin, LocalDateTime.now(), null);
    }
}
