package store.csolved.csolved.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.controller.request.UserProfileRequest;
import store.csolved.csolved.domain.user.service.UserProfileService;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class UserProfileController
{
    private final UserProfileService profileService;

    //    @InitBinder("updateProfileForm")
//    public void initBinder(WebDataBinder binder)
//    {
//        binder.addValidators(updateProfileValidator);
//    }

    //    @LoginRequest
    @GetMapping("/profile")
    public String initUpdateProfile(@LoginUser User user,
                                    Model model)
    {
        model.addAttribute("updateProfileForm", UserProfileRequest.from(user));
        return null;
    }

    //    @LoginRequest
    @PostMapping("/profile")
    public String getUser(@Valid @ModelAttribute("updateProfileForm") UserProfileRequest form,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) throws IOException
    {
        if (result.hasErrors())
        {
            profileService.restoreProfile(form);
            return null;
        }

        profileService.updateProfile(form);
        redirectAttributes.addFlashAttribute("success", true);
        return null;
    }
}
