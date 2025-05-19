package com.blendonclass.control;

import com.blendonclass.dto.AlarmListDto;
import com.blendonclass.dto.LessonDetailDto;
import com.blendonclass.dto.LessonDto;
import com.blendonclass.service.AlarmService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final LessonService lessonService;
    private final AlarmService alarmService;

    @GetMapping("/")
    public String home() {
        return "redirect:/student/main"; // 학생 메인 페이지로 리다이렉트
    }

    // 학생 메인 페이지 + 모든 정보 로드
    @GetMapping("/main") // URL을 "/student/main"으로 변경 (더 직관적)
    public String studentMain(Model model, Principal principal) {
        Long id = Long.parseLong(principal.getName());

        LessonDto lastLessonDto = lessonService.getLastLesson(id);
        model.addAttribute("lastLesson", lastLessonDto);

        //알림
        List<AlarmListDto> alarmListDtos = alarmService.getAlarmByAccountId(id);

        model.addAttribute("selectedGrade", lastLessonDto.getGrade());
        model.addAttribute("selectedSubject", lastLessonDto.getSubject());
        model.addAttribute("alarmListDtos", alarmListDtos);

        return "studentMain"; // 학생 메인 페이지로 이동
    }


}




