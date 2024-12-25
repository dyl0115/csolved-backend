package store.csolved.csolved.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import store.csolved.csolved.user.dto.SignInForm;
import store.csolved.csolved.user.dto.SignUpForm;
import store.csolved.csolved.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordUtils;

@RequiredArgsConstructor
@Component
public class AuthValidator
{
    private final UserMapper userMapper;

    public void checkSignUpForm(SignUpForm form, BindingResult errors)
    {
        checkEmailDuplicate(form, errors);
        checkNicknameDuplicate(form, errors);
        checkPasswordMatch(form, errors);
    }

    public void checkUserExist(SignInForm form, BindingResult errors)
    {
        String storedPassword = userMapper.findPasswordByEmail(form.getEmail());
        if (storedPassword == null || !PasswordUtils.verifyPassword(form.getPassword(), storedPassword))
        {
            errors.reject("notExist");
        }
    }

    private void checkEmailDuplicate(SignUpForm form, BindingResult errors)
    {
        if (userMapper.existsByEmail(form.getEmail()))
        {
            errors.rejectValue("email", "duplicate");
        }
    }

    private void checkNicknameDuplicate(SignUpForm form, BindingResult errors)
    {
        if (userMapper.existsByNickname(form.getNickname()))
        {
            errors.rejectValue("nickname", "duplicate");
        }
    }

    private void checkPasswordMatch(SignUpForm form, BindingResult errors)
    {
        if (!form.getPassword().equals(form.getPasswordConfirm()))
        {
            errors.rejectValue("passwordConfirm", "notMatch");
        }
    }
}
