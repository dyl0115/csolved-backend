package store.csolved.csolved;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.csolved.csolved.domain.file.FileService;
import store.csolved.csolved.domain.user.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController2
{
    private final FileService fileService;

    @GetMapping("/test2")
    public String testView(Model model)
    {
        model.addAttribute("user", User.builder().build());
        return "/layout/create-layout";
    }
}
