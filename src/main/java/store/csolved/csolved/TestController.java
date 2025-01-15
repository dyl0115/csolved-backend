package store.csolved.csolved.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController
{
    @GetMapping("/view")
    public String testView()
    {
        return "/fragments/common/logo";
    }
}
