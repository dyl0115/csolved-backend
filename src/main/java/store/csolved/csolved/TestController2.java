package store.csolved.csolved;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import store.csolved.csolved.domain.file.FileService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@Controller
public class TestController2
{
    private final FileService fileService;

    @GetMapping("/test2")
    public String testView(Model model)
    {
        model.addAttribute("user", User.builder().build());
        return "create-update-layout"; 
    }
}
