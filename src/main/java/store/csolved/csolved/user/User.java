package store.csolved.csolved.user;

import lombok.*;
import store.csolved.csolved.user.exceptions.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class User
{
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String company;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

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

    public void updateEmail(String email)
    {
        validateEmailFormat(email);
        this.email = email;
    }

    public void updatePassword(String password)
    {
        validatePasswordFormat(password);
        this.password = password;
    }

    public void updateNickname(String nickname)
    {
        validateNicknameFormat(nickname);
        this.nickname = nickname;
    }

    public void updateCompany(String company)
    {
        this.company = company;
    }

    public void delete()
    {
        if (this.deletedAt == null)
        {
            this.deletedAt = LocalDateTime.now();
        }
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

        if (!password.equals("비밀번호 포맷 정규식 아직 안 정함 ㅋㅋ"))
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
