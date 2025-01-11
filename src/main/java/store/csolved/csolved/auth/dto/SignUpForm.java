package store.csolved.csolved.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.csolved.csolved.domain.user.User;

@Data
@NoArgsConstructor
public class SignUpForm
{
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()]{8,20}$",
            message = "비밀번호는 8~20자의 영문자, 숫자 조합이어야 합니다. 특수문자(!@#$%^&*())도 사용 가능합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "길이가 2에서 10 사이여야 합니다.")
    private String nickname;

    public User toUser()
    {
        return User.create(email, password, nickname,
                null, false);
    }
}
