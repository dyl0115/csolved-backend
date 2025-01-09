package store.csolved.csolved.auth.argumentResolver;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.csolved.csolved.auth.AuthConstants;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver
{
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasUserClass = parameter.getParameterType().equals(User.class);

        return hasLoginUserAnnotation && hasUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
    {
        return httpSession.getAttribute(AuthConstants.LOGIN_USER_SESSION_KEY);
    }
}
