package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProgressController {
    private final LessonService lessonService;

    @GetMapping("/teacher/setprogress")
    public String getProgressSetPage(@RequestParam("id") Long classroomId, Model model) {
        lessonService.getAllLessons(1, SUBJECT.MATH);
        model.addAttribute("lessons", lessonService.getAllLessons(1, SUBJECT.MATH));
        return "setprogress";
    }
}
