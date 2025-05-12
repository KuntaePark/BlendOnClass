package com.blendonclass.control;

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.LessonDetailDto;
import com.blendonclass.dto.LessonDto;
import com.blendonclass.service.CustomUserDetails;
import com.blendonclass.service.LessonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;


    // 메인 학습 화면: 최근 수강 강의 + 마지막 강의 정보
    @GetMapping("/main")
    public String showLessonMain(
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) String subject,
            Model model,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Long loggedId = Long.parseLong(customUserDetails.getUsername());

        // 모든 경우 selectedGrade, selectedSubject는 모델에 넣기
        model.addAttribute("selectedGrade", grade);
        model.addAttribute("selectedSubject", subject);

        // 과목 선택되었을 때만 챕터 로딩
        if (grade != null && subject != null) {
            SUBJECT subjectEnum = SUBJECT.valueOf(subject.toUpperCase());
            model.addAttribute("chapters", lessonService.getAllChapters(grade, subjectEnum, loggedId));
        }

        return "lessonMain";
    }

    // 다시하기 (진도 초기화 후 상세로 이동)
    @GetMapping("/restart/{lessonId}")
    public String restartLesson(@PathVariable Long lessonId,
                                HttpSession session,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            Long accountId = Long.parseLong(customUserDetails.getUsername());
            // lessonService.resetProgress(accountId, lessonId);
            return "redirect:/student/lesson/continue/" + lessonId;
        } else {
            return "redirect:/login";
        }
    }

    // 이어하기 (상세페이지 진입)
    @GetMapping("/continue/{lessonId}")
    public String continueLesson(@PathVariable Long lessonId, Model model) {
        LessonDetailDto detailDto = lessonService.getLessonDetail(lessonId);
        model.addAttribute("lessonDetail", detailDto);

        model.addAttribute("prevLessonId", lessonService.getPrevLessonId(lessonId));
        model.addAttribute("nextLessonId", lessonService.getNextLessonId(lessonId));

        return "lessonDetail";
    }
}
