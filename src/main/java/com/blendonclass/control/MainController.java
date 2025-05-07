package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Getter@Setter
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        ROLE role = (ROLE)session.getAttribute("role");
        String redirectUrl = switch (role) {
            case ADMIN -> "/admin";
            case TEACHER -> "/teacher";
            case STUDENT -> "/student";
        };
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/student")
    public String student(Model model) {return "studentMain";}

    @GetMapping("/teacher")
    public String teacher(Model model) {return "teacherMain";}

    @GetMapping("/admin")
    public String admin(Model model) {return "redirect:/admin/accounts";}
}