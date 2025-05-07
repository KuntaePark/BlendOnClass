package com.blendonclass.control;

import com.blendonclass.dto.QuizAnsweredDto;
import com.blendonclass.dto.QuizDetailDto;
import com.blendonclass.dto.QuizGradedDto;
import com.blendonclass.service.CustomUserDetails;
import com.blendonclass.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    // 소단원 퀴즈 시작
    @GetMapping("/start")
    public String startLessonQuiz(@RequestParam Long lessonId,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  HttpSession session, Model model) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        List<QuizDetailDto> quizList = quizService.getQuiz(lessonId, accountId, session);

        model.addAttribute("quizList", quizList);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("currentIndex", 0);
        return"quiz";
    }

    // 대단원 시험 시작
    @GetMapping("/chapter")
    public String startChapterQuiz(@RequestParam Long chapterId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   HttpSession session, Model model) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        List<QuizDetailDto> quizList = quizService.getChapterQuiz(chapterId, accountId, session);

        model.addAttribute("quizList", quizList);
        model.addAttribute("chapterId", chapterId);
        model.addAttribute("currentIndex", 0);
        return "quiz";
    }

    // 문제 채점
    @PostMapping("/grade")
    @ResponseBody
    public QuizGradedDto gradeQuiz(@RequestBody QuizAnsweredDto answeredDto,
                                   @RequestParam Long lessonId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        return quizService.gradeQuiz(answeredDto, lessonId, accountId);
    }

    // 퀴즈 종료
    @PostMapping("/end")
    @ResponseBody
    public int endQuiz(@RequestParam Long lessonId,
                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        return quizService.endQuiz(accountId, lessonId);
    }

}
