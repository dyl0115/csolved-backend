package store.csolved.csolved.auth.etc;

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
        boolean isUserClass = parameter.getParameterType().equals(User.class);
        return hasLoginUserAnnotation && isUserClass;
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer, // 이 매개변수가 핵심임!!
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory)
    {
        User user = (User) httpSession.getAttribute(AuthConstants.LOGIN_USER_SESSION_KEY);

        // mavContainer 자체에 넣어버리고 해당 참조 값을 반환하면,
        // 컨트롤러 내에서 업데이트 된 user객체가 view로 전달된다.
        if (mavContainer != null) mavContainer.addAttribute("user", user);
        return user;
    }
}
