package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ChapterLessonDto;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProgressController {
    private final LessonService lessonService;

    @GetMapping("/teacher/progress")
    public String getProgressSetPage(@RequestParam("id") Long classroomId,
                                     @RequestParam("subject")
                                     Optional<SUBJECT> subject,
                                     Model model) {
        //todo - 해당 과목의 progress 찾기

        List<ChapterLessonDto> chapterLessonDtos = subject.isEmpty() ? null :
                lessonService.getAllLessons(1, subject.get());
        model.addAttribute("lessons", chapterLessonDtos);
        model.addAttribute("classroomId", classroomId);
        return "progress";
    }

    @PostMapping("/teacher/setprogress")
    public String setProgress(@RequestParam Long classroomId,
                              @RequestParam Long startId,
                              @RequestParam Long endId,
                              Model model) {
        System.out.println(classroomId +" " + startId + " " + endId);
        //todo - progress 저장
        return "redirect:/teacher/progress?id="+classroomId;
    }
}
