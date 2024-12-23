package store.csolved.csolved.user;

import lombok.*;
import store.csolved.csolved.user.exceptions.*;

import java.time.LocalDateTime;

@Getter
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

    @Builder(access = AccessLevel.PRIVATE)
    private User(Long id,
                 String email,
                 String password,
                 String nickname,
                 String company,
                 Boolean isAdmin,
                 LocalDateTime createdAt,
                 LocalDateTime deletedAt)
    {
        validateEmailFormat(email);
        validatePasswordFormat(password);
        validateNicknameFormat(nickname);

        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.company = company;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.isAdmin = isAdmin;
    }

    public static User create(String email, String password, String nickname, String company, boolean isAdmin)
    {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .company(company)
                .isAdmin(isAdmin)
                .createdAt(LocalDateTime.now())
                .deletedAt(null)
                .build();
    }

    public static User create(String email, String password, String nickname)
    {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .isAdmin(false)
                .createdAt(LocalDateTime.now())
                .deletedAt(null)
                .build();
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

    public void updateAdmin(Boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public void delete()
    {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(User user)
    {
        this.email = user.email;
        this.password = user.password;
        this.nickname = user.nickname;
        this.company = user.company;
        this.isAdmin = user.isAdmin;
        this.createdAt = user.createdAt;
        this.deletedAt = user.deletedAt;
    }

    private void validateEmailFormat(String email)
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

    private void validatePasswordFormat(String password)
    {
        if (password == null)
        {
            throw new EmptyPasswordException();
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&!+=])(?=\\S+$).{6,20}$";
        if (!password.matches(passwordRegex))
        {
            throw new InvalidPasswordFormatException();
        }
    }

    private void validateNicknameFormat(String nickname)
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
