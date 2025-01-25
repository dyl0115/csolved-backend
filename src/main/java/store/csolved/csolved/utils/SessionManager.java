package store.csolved.csolved.utils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Component
@SessionScope
public class SessionManager
{
    private final static String LOGIN_USER_SESSION_KEY = "loginUser";
    private final HttpSession httpSession;

    public void setLoginUser(User user)
    {
        httpSession.setAttribute(LOGIN_USER_SESSION_KEY, user);
    }

    public User getLoginUser()
    {
        return (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
    }

    public void invalidateSession()
    {
        httpSession.invalidate();
    }
}
