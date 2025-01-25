package store.csolved.csolved.domain.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import store.csolved.csolved.domain.user.User;

@Getter
@Builder
public class UserProfileForm
{
    private Long userId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "길이가 2에서 10 사이여야 합니다.")
    private String nickname;

    private MultipartFile profileImage;

    public static UserProfileForm from(User user)
    {
        return UserProfileForm.builder()
                .nickname(user.getNickname())
                .build();
    }
}
