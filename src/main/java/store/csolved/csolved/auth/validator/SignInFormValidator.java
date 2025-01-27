package store.csolved.csolved.auth.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import store.csolved.csolved.auth.controller.dto.SignInForm;
import store.csolved.csolved.domain.user.mapper.UserMapper;
import store.csolved.csolved.utils.PasswordManager;

@RequiredArgsConstructor
@Component
public class SignInFormValidator implements Validator
{
    private final PasswordManager passwordManager;
    private final UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return SignInForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        SignInForm form = (SignInForm) target;
        validateUserCredential(form, errors);
    }

    private void validateUserCredential(SignInForm form, Errors errors)
    {
        String storedPassword = userMapper.findPasswordByEmail(form.getEmail());
        if (storedPassword == null || !passwordManager.verifyPassword(form.getPassword(), storedPassword))
        {
            errors.reject("notExist", "이메일과 비밀번호를 확인해주세요.");
        }
    }
}
