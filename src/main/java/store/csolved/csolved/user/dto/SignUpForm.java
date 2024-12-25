package store.csolved.csolved.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.csolved.csolved.user.User;

@Setter
@Getter
@NoArgsConstructor
public class SignUpForm
{
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()]{8,20}$",
            message = "비밀번호는 8~20자의 영문자, 숫자 조합이어야 합니다. 특수문자(!@#$%^&*())도 사용 가능합니다.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    @Size(min = 2, max = 10)
    private String nickname;

    public User toUser()
    {
        return User.create(email, password, nickname,
                null, false);
    }
}
