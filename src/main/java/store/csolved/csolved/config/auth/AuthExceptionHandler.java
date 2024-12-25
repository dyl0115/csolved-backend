package store.csolved.csolved.config.auth;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler
{
    @ExceptionHandler(UnAuthenticatedException.class)
    public String handleUnAuthenticate()
    {
        return "redirect:/auth";
    }
}
