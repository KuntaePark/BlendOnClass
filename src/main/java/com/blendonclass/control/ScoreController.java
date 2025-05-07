package com.blendonclass.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ScoreController {

    @GetMapping("/student/myscore")
    public String getStudentScorePage(Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());
        //todo - 해당 학생의 전체 성적 불러오기
        return "score";
    }

    @GetMapping("/teacher/myscore")
    public String getClassroomScorePage(@RequestParam("id") Long classroomId, Model model) {

        return "score";
    }
}
