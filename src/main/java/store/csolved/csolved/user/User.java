package store.csolved.csolved.user;

import lombok.*;
import store.csolved.csolved.user.exceptions.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class User
{
    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final String company;
    private final Boolean isAdmin;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public static User create(String email, String password, String nickname, String company, boolean isAdmin)
    {
        validateFormat(email, password, nickname);
        return new User(null, email, password, nickname, company, isAdmin, LocalDateTime.now(), null);
    }

    public static User create(String email, String password, String nickname)
    {
        validateFormat(email, password, nickname);
        return create(email, password, nickname, null, false);
    }

    public User updateEmail(String email)
    {
        validateEmailFormat(email);
        return new User(this.id, email, this.password, this.nickname, this.company, this.isAdmin, this.createdAt, this.deletedAt);
    }

    public User updatePassword(String password)
    {
        validatePasswordFormat(password);
        return new User(this.id, this.email, password, this.nickname, this.company, this.isAdmin, this.createdAt, this.deletedAt);
    }

    public User updateNickname(String nickname)
    {
        validateNicknameFormat(nickname);
        return new User(this.id, this.email, this.password, nickname, this.company, this.isAdmin, this.createdAt, this.deletedAt);
    }

    public User updateCompany(String company)
    {
        return new User(this.id, this.email, this.password, this.nickname, company, this.isAdmin, this.createdAt, this.deletedAt);
    }

    public User delete()
    {
        return new User(this.id, this.email, this.password, this.nickname,
                this.company, this.isAdmin, this.createdAt, LocalDateTime.now());
    }

    public boolean isDeleted()
    {
        return this.deletedAt != null;
    }

    private static void validateFormat(String email, String password, String nickname)
    {
        validateEmailFormat(email);
        validatePasswordFormat(password);
        validateNicknameFormat(nickname);
    }

    private static void validateEmailFormat(String email)
    {
        if (email == null)
        {
            throw new EmptyEmailException();
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex))
        {
            throw new InvalidEmailFormatException();
        }
    }

    private static void validatePasswordFormat(String password)
    {
        if (password == null)
        {
            throw new EmptyPasswordException();
        }

        if (password.equals("이상한 포맷"))
        {
            throw new InvalidPasswordFormatException();
        }
    }

    private static void validateNicknameFormat(String nickname)
    {
        if (nickname == null)
        {
            throw new EmptyNicknameException();
        }
        if (nickname.length() > 20)
        {
            throw new InvalidNicknameFormatException();
        }
    }
}
