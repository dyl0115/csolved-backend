package store.csolved.csolved.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.controller.form.UserProfileForm;
import store.csolved.csolved.domain.user.service.UserProfileService;
import store.csolved.csolved.validator.UpdateProfileValidator;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController
{
    public final static String VIEWS_USER_PROFILE_UPDATE = "/views/user-profile/profile-update";

    private final UserProfileService profileService;
    private final UpdateProfileValidator updateProfileValidator;

    @InitBinder("updateProfileForm")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(updateProfileValidator);
    }

    @LoginRequest
    @GetMapping("/users/profile")
    public String initUpdateProfile(@LoginUser User user,
                                    Model model)
    {
        model.addAttribute("updateProfileForm", UserProfileForm.from(user));
        return VIEWS_USER_PROFILE_UPDATE;
    }

    @LoginRequest
    @PostMapping("/users/profile")
    public String getUser(@Valid @ModelAttribute("updateProfileForm") UserProfileForm form,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) throws IOException
    {
        if (result.hasErrors())
        {
            profileService.restoreProfile(form);
            return VIEWS_USER_PROFILE_UPDATE;
        }

        profileService.updateProfile(form);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/users/profile";
    }
}