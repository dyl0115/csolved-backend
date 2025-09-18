package store.csolved.csolved.domain.auth.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SigninCommand
{
    String email;
    String password;
}
