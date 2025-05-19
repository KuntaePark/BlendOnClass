package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ChapterDto;
import com.blendonclass.dto.ChapterLessonDto;
import com.blendonclass.dto.ProgressDto;
import com.blendonclass.entity.Progress;
import com.blendonclass.service.ClassroomService;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProgressController {
    private final LessonService lessonService;
    private final ProgressService progressService;
    private final ClassroomService classroomService;

    @GetMapping("/teacher/progress")
    public String getProgressSetPage(@RequestParam("id") Long classroomId,
                                     @RequestParam(value = "subject", required = false)
                                     SUBJECT subject,
                                     Model model) {
        //todo - 해당 과목의 progress 찾기
        List<ChapterLessonDto> chapterLessonDtos = null;
        List<ChapterDto> chapterDtos = null;
        ProgressDto progressDto = null;
        int grade = classroomService.getGradeOfClassroom(classroomId);

        if(subject != null) {
            chapterLessonDtos = lessonService.getAllLessons(grade, subject);
            Progress progress = progressService.getProgress(classroomId, subject);
            progressDto = progress != null ? ProgressDto.from(progress) : new ProgressDto();
            chapterDtos = lessonService.getChapterWithLessons(1,subject);
        }

        model.addAttribute("chapters", chapterDtos);
        model.addAttribute("lessons", chapterLessonDtos);
        model.addAttribute("subject", subject);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("progress", progressDto);
        return "progress";
    }

    @PostMapping("/teacher/setprogress")
    public ResponseEntity<String> setProgress(@RequestBody ProgressDto progressDto) {
        System.out.println(progressDto);
        progressService.setProgress(progressDto);
        //todo - progress 저장
        return ResponseEntity.ok("저장 성공");
    }
}
