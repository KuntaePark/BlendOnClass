package com.blendonclass.control;


import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Classroom;
import com.blendonclass.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final AuthorityService authorityService;

    @GetMapping("/classrooms")
    public String getTeacherClassrooms(
            @RequestParam("accountId") Long accountId,
            @RequestParam(value = "subjects", required = false)List<SUBJECT> subjects, Model model) {
        // 테스트용 subjects 기본값 (HR, ENG)
        if (subjects == null || subjects.isEmpty()) {
            subjects = Arrays.asList(SUBJECT.HR, SUBJECT.ENG);
        }

        List<Classroom> classrooms = authorityService.getClassroomsByAccountIdAndSubjects(accountId, subjects);

        model.addAttribute("accountId", accountId);
        model.addAttribute("subjects", subjects);
        model.addAttribute("classrooms", classrooms);

        return "teacher/classroom-list"; // templates/teacher/classroom-list.html
    }
}

