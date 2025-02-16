package store.csolved.csolved.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.csolved.csolved.domain.user.controller.view_model.BookmarksAndPage;
import store.csolved.csolved.domain.user.service.UserActivityFacade;
import store.csolved.csolved.utils.login.LoginRequest;
import store.csolved.csolved.utils.login.LoginUser;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.controller.form.UserProfileForm;
import store.csolved.csolved.domain.user.service.UserProfileService;
import store.csolved.csolved.utils.page.PageInfo;
import store.csolved.csolved.validator.UpdateProfileValidator;

import java.io.IOException;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class UserController
{
    public static final String VIEWS_USER_PROFILE = "/views/user-profile/profile-update";
    public static final String VIEWS_USER_ACTIVITY = "/views/user-profile/activity";

    private final UserProfileService profileService;
    private final UserActivityFacade userActivityFacade;
    private final UpdateProfileValidator updateProfileValidator;

    @InitBinder("updateProfileForm")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(updateProfileValidator);
    }

    @LoginRequest
    @GetMapping("/activity")
    public String getBookmarksAndPage(@LoginUser User user,
                                      @PageInfo(type = "bookmarkPage") Long page,
                                      Model model)
    {
        BookmarksAndPage bookmarksAndPage = userActivityFacade.getBookmarksAndPage(user.getId(), page);
        model.addAttribute("bookmarksAndPage", bookmarksAndPage);
        return VIEWS_USER_ACTIVITY;
    }

    @LoginRequest
    @GetMapping("/profile")
    public String initUpdateProfile(@LoginUser User user,
                                    Model model)
    {
        model.addAttribute("updateProfileForm", UserProfileForm.from(user));
        return VIEWS_USER_PROFILE;
    }

    @LoginRequest
    @PostMapping("/profile")
    public String getUser(@Valid @ModelAttribute("updateProfileForm") UserProfileForm form,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) throws IOException
    {
        if (result.hasErrors())
        {
            profileService.restoreProfile(form);
            return VIEWS_USER_PROFILE;
        }

        profileService.updateProfile(form);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/users/profile";
    }
}