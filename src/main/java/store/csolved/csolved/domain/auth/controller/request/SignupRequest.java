package store.csolved.csolved.domain.auth.controller.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import store.csolved.csolved.domain.auth.service.command.SignupCommand;

@Data
public class SignupRequest
{
    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 2, max = 8)
    String nickname;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()]{8,20}$")
    String password;

    public SignupCommand toCommand()
    {
        return SignupCommand.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }
}
