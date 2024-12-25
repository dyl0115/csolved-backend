package store.csolved.csolved.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserSignInForm
{
    private String email;
    private String password;
}
