package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import com.blendonclass.dto.ClassroomListDto;
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

        List<ClassroomListDto> classroomListDtos = authorityService.getClassroomsByAccountId(id);
        model.addAttribute("classroomListDtos", classroomListDtos);
        //todo - 반 바뀔 떄마다 alarm 및 진도율 재로드
        return "teacherMain";
    }


    @GetMapping("/admin")
    public String admin(Model model) {return "redirect:/admin/accounts";}
}