package com.blendonclass.control;

import com.blendonclass.service.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
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
    public String index(HttpSession session, Model model) {
        model.addAttribute("id",session.getAttribute("id"));
        model.addAttribute("name",session.getAttribute("name"));
        model.addAttribute("email",session.getAttribute("email"));
        return "index";
    }

    @GetMapping("/success")
    public String success(Principal principal, Model model) {
        String user = principal.getName();
        model.addAttribute("user", user);
        return "test";
    }
}
