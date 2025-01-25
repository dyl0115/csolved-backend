package store.csolved.csolved.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.csolved.csolved.domain.user.controller.dto.UserProfileForm;
import store.csolved.csolved.exception.ImageUploadException;
import store.csolved.csolved.file.S3Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserProfileService
{
    private final static String FOLDER_NAME_USER_PROFILE = "user";

    private final S3Service s3Service;
    private final UserService userService;

    public void updateProfile(UserProfileForm form) throws IOException
    {
        MultipartFile profileImage = form.getProfileImage();

        if (!profileImage.isEmpty())
        {
            String profileUrl = s3Service.upload(profileImage, FOLDER_NAME_USER_PROFILE);
            if (profileUrl == null)
            {
                throw new ImageUploadException("이미지 저장 실패");
            }
            userService.updateProfile(form.getUserId(), profileUrl);
        }
        userService.updateNickname(form.getUserId(), form.getNickname());
    }
}
