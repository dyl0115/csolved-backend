package store.csolved.csolved.auth.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import store.csolved.csolved.auth.annotation.LoginRequest;
import store.csolved.csolved.domain.user.User;

import static store.csolved.csolved.auth.AuthConstants.*;


@RequiredArgsConstructor
@Component
public class LoginRequestInterceptor implements HandlerInterceptor
{
    private final HttpSession httpSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // 핸들러매핑에서 매칭되는 컨트롤러가 없는 경우에는 그대로 진행해야 한다. ex) 404예외
        if (!(handler instanceof HandlerMethod controllerMethod)) return true;

        // 핸들러매핑에서 매칭되는 컨트롤러에 @LoginRequest가 있는지 확인
        boolean hasRequestLoginAnnotation = controllerMethod
                .hasMethodAnnotation(LoginRequest.class);

        // 컨트롤러 메서드에 @RequestLogin이 없으면 그대로 진행
        if (!hasRequestLoginAnnotation) return true;

        // 컨트롤러 메서드에 @RequestLogin이 있고, 로그아웃 상태라면 로그인 화면으로 리다이렉트
        User user = (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
        if (user == null)
        {
            response.sendRedirect(LOGIN_PAGE_URL);
            return false;
        }

        // 컨트롤러 메서드에 @RequestLogin이 있고, 로그인 상태라면 그대로 진행
        return true;
    }
}
