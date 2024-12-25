package store.csolved.csolved.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignInForm
{
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
