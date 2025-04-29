package com.blendonclass.control;

import com.blendonclass.entity.Quiz;
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
    private final QuizRepository quizRepository;

    @GetMapping("/")
    public String index(Principal principal, HttpSession session, Model model) {
        Long id = Long.parseLong(principal.getName());
        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("email", session.getAttribute("email"));
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/success")
    public String success(Principal principal, Model model) {
        String user = principal.getName();
        model.addAttribute("user", user);
        return "test";
    }

    @GetMapping("/test")
    public String getQuiz(Model model) {
        List<Quiz> quizList = quizRepository.findAll();
        quizList.forEach((quiz) -> {
            System.out.println(quiz.getAnswer());
            System.out.println(quiz.getSolution());
            System.out.println(quiz.getContextJson());
        });
        model.addAttribute("quizList", quizList);
        return "test";
    }

    @GetMapping("/student")
    public String student(Model model) {return "studentMain";}

    @GetMapping("/teacher")
    public String teacher(Model model) {return "teacherMain";}

    @GetMapping("/admin")
    public String admin(Model model) {return "adminMain";}
}