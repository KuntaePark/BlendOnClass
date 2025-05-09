package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonScoreDto;
import com.blendonclass.dto.ScoreDataDto;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.ScoreService;
import lombok.RequiredArgsConstructor;
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

    //todo - 함수겹침, 정리?
    @GetMapping("/student/myscore")
    public String getStudentScorePage(Principal principal, Model model) {
        Long id = Long.parseLong(principal.getName());
        
        //전체 성적 정보 불러오기
        getScore(model, id,1, SUBJECT.MATH, true);
        return "score";
    }

    @GetMapping("/teacher/myscore")
    public String getClassroomScorePage(@RequestParam("id") Long classroomId, Model model) {

        //반 전체 성적 정보 불러오기
        getScore(model, classroomId, 1, SUBJECT.MATH, false);
        return "score";
    }

    public void getScore(Model model, Long id, int grade, SUBJECT subject, boolean isStudent) {
        //전체 성적 정보 불러오기
        long startTime = System.nanoTime();
        List<ScoreDataDto> scoreDataDtos = scoreService.getScoreData(id, grade, subject, isStudent);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("실행 시간:"+duration/1e+9 +"s");

//        startTime = System.nanoTime();
//        List<LessonScoreDto> lessonScoreDtos = scoreService.getAllLessonScores(grade, subject, id);
//        endTime = System.nanoTime();
//        duration = endTime - startTime;
//        System.out.println("실행 시간2:"+duration/1e+9 +"s");


        List<String> subjectList = Arrays.stream(SUBJECT.values()).map(Enum::name).collect(Collectors.toList());
        subjectList.remove("HR");
        model.addAttribute("scoreDataDtos", scoreDataDtos);
        model.addAttribute("subjectList", subjectList);
    }
}
