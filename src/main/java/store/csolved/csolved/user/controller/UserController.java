package store.csolved.csolved.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.csolved.csolved.user.dto.UserSignInForm;
import store.csolved.csolved.user.dto.SignUpForm;
import store.csolved.csolved.user.dto.UserInfo;
import store.csolved.csolved.user.exceptions.SignUpFailedException;
import store.csolved.csolved.user.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController
{
    private final UserService userService;

    @GetMapping("/users/auth")
    public String authForm(Model model)
    {
        model.addAttribute("signUpForm", new SignUpForm());
        return "auth";
    }

    @PostMapping("/users/signup")
    public String signUp(@ModelAttribute SignUpForm form, BindingResult bindingResult)
    {
        try
        {
            userService.signUp(form);
        }
        catch (SignUpFailedException ex)
        {
            ex.getErrors().forEach((field, errorCode) -> bindingResult.rejectValue(field, errorCode));
            return "auth";
        }
        return "redirect:/users/auth";
    }

    @PostMapping("/users/signin")
    public String signIn(HttpSession session, @ModelAttribute UserSignInForm form)
    {
        UserInfo user = userService.signIn(form);
        session.setAttribute("user", user);
        return "redirect:/questions";
    }
}
