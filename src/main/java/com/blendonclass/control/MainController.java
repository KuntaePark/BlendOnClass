package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import com.blendonclass.service.AlarmService;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.ClassroomService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Getter@Setter
@RequiredArgsConstructor
public class MainController {

    private final AuthorityService authorityService;
    private final AlarmService alarmService;


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
    public String student(Model model) {return "redirect:/student/main";}

    @GetMapping(value={"/teacher", "/teacher/{id}"})
    public String teacher(@PathVariable("id") Optional<Long> cid, Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());

        // Authority에서 반 + 과목을 함께 가져옴
        List<Authority> authorities = authorityService.getAuthoritiesByAccountId(id);
        for(Authority auth : authorities) {
            System.out.println(auth);
        }
        // 경로변수 id가 없으면, 첫 번째 권한의 반을 기본으로 사용
        Long classroomId = cid.orElse(authorities.get(0).getClassroom().getId());

        model.addAttribute("authorities", authorities); // authority 객체 전체 전달
        model.addAttribute("ar", alarmService.getAlarmByClassroomId(classroomId));
        model.addAttribute("classroomId", classroomId);

        return "teacherMain";
    }


    @GetMapping("/admin")
    public String admin(Model model) {return "redirect:/admin/accounts";}
}