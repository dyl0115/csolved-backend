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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.csolved.csolved.domain.tag.entity.Tag;
import store.csolved.csolved.file.S3Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController2
{
    private final S3Service s3Service;

    @GetMapping("/test2")
    public String testView(Model model)
    {
        TestForm form = TestForm.builder().build();

        model.addAttribute("createForm", form);
        return "views/domain/free-board/create";
    }

    @PostMapping("/test2/save")
    public String save(@ModelAttribute("createForm") TestForm form)
    {
        return "views/domain/free-board/detail";
    }

    @PostMapping("/api/upload-image")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> imageSave(@RequestParam("file") MultipartFile file)
    {
        try
        {
            String imageUrl = s3Service.upload(file, "post/free-board");
            HashMap<String, String> response = new HashMap<>();
            response.put("location", imageUrl);
            return ResponseEntity.ok(response);
        }
        catch (IOException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Getter
    @Builder
    public static class TestForm
    {
        private String content;
    }
}
