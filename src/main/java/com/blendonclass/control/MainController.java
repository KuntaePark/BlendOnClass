package com.blendonclass.control;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Getter@Setter
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
