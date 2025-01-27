package store.csolved.csolved.domain.user.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import store.csolved.csolved.domain.user.controller.dto.UserProfileForm;
import store.csolved.csolved.domain.user.service.UserService;

@RequiredArgsConstructor
@Component
public class UserUpdateProfileValidator implements Validator
{
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return UserProfileForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        UserProfileForm form = (UserProfileForm) target;

        //닉네임 중복 체크
        if (userService.existNickname(form.getNickname()))
        {
            errors.rejectValue("nickname", "duplicate", "이미 사용 중인 닉네임입니다.");
        }
    }
}