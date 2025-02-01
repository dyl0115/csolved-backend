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
import store.csolved.csolved.domain.auth.etc.annotation.LoginRequest;
import store.csolved.csolved.domain.auth.etc.annotation.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.controller.dto.UserProfileForm;
import store.csolved.csolved.domain.user.service.UserProfileService;
import store.csolved.csolved.domain.user.validator.UpdateProfileValidator;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController
{
    public final static String VIEWS_USER_PROFILE_UPDATE = "views/domain/user-profile/update";

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
                          BindingResult result) throws IOException
    {
        if (result.hasErrors())
        {
            profileService.restoreProfile(form);
            return VIEWS_USER_PROFILE_UPDATE;
        }
        profileService.updateProfile(form);
        return "redirect:/users/profile";
    }
}