package com.blendonclass.control;

import com.blendonclass.service.CustomUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Getter@Setter
public class MainController {
    @GetMapping("/")
    public String index(Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());
        model.addAttribute("id", id);
        return "index";
    }

    @GetMapping("/success")
    public String success(Principal principal, Model model) {
        String user = principal.getName();
        model.addAttribute("user", user);
        return "test";
    }
}
