package com.blendonclass.control;

import com.blendonclass.entity.Account;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import com.blendonclass.entity.Quiz;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomRepository;
import com.blendonclass.repository.QuizRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Getter@Setter
@RequiredArgsConstructor
public class MainController {
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