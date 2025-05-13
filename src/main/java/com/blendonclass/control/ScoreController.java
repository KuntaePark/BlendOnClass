package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.dto.ScoreDataDto;
import com.blendonclass.repository.ClassroomRepository;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;
    private final LessonService lessonService;
    private final ClassroomRepository classroomRepository;

    //todo - 함수겹침, 정리?
    @GetMapping("/student/myscore")
    public String getStudentScorePage() {
        return "score";
    }

    @GetMapping("/teacher/myscore")
    public String getClassroomScorePage(@RequestParam("id") Long classroomId,
                                        Model model) {
        int grade = classroomRepository.findGradeById(classroomId);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("grade", grade);
        return "score";
    }

    @GetMapping("/getScore")
    public ResponseEntity<List<?>> getScore(@RequestParam(value = "id",required = false) Long classroomId,
                                                   @RequestParam("grade") int grade,
                                                   @RequestParam("subject") SUBJECT subject,
                                                   Principal principal) {
        boolean isStudent = classroomId==null;
        Long id = classroomId;

        if(classroomId == null) {
            //학생
            id = Long.parseLong(principal.getName());
        }

        System.out.println(id+" "+ subject + " "+ grade);
        long startTime = System.nanoTime();
        List<?> result = scoreService.getAllLessonScores(grade, subject, id, isStudent);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("실행 시간2:"+duration/1e+9 +"s");

        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}
