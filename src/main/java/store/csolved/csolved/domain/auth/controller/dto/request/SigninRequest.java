package store.csolved.csolved.domain.auth.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import store.csolved.csolved.domain.auth.service.dto.SigninCommand;
import store.csolved.csolved.domain.auth.service.dto.SignupCommand;

@Data
public class SigninRequest
{
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public SigninCommand toCommand()
    {
        return SigninCommand.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
