package store.csolved.csolved.user;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String company;
    private LocalDateTime createdAt;
    private boolean isAdmin;
}
