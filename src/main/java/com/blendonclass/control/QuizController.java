package com.blendonclass.control;

import com.blendonclass.dto.*;
import com.blendonclass.dto.QuizAnsweredDto;
import com.blendonclass.dto.QuizDetailDto;
import com.blendonclass.service.CustomUserDetails;
import com.blendonclass.service.LessonService;
import com.blendonclass.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final LessonService lessonService;


    // 강의메인으로 이동
    @GetMapping("/lessonMain")
    public String lessonMainPage() {
        return "lessonMain"; // templates/lessonMain.html을 렌더링
    }

    // 소단원 퀴즈 시작
    @GetMapping("/start")
    public String startLessonQuiz(@RequestParam Long lessonId,
                                  @RequestParam(required = false) Integer grade,
                                  @RequestParam(required = false) String subject,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  HttpSession session, Model model) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        List<QuizDetailDto> quizList = quizService.getQuiz(lessonId, accountId, session);

        if (quizList == null || quizList.isEmpty()) {
            model.addAttribute("errorMessage", "문제가 존재하지 않습니다.");
            return "quiz-error"; // 👉 에러 화면 따로 만들거나 메시지 처리
        }

        QuizDetailDto currentQuiz = quizList.get(0);

        LessonDetailDto lessonDetail = lessonService.getLessonDetailSafe(lessonId);
        model.addAttribute("lessonTitle", lessonDetail.getTitle());


        model.addAttribute("quizList", quizList);
        model.addAttribute("currentQuiz", currentQuiz);
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("currentIndex", 0);
        model.addAttribute("grade", grade);
        model.addAttribute("subject", subject);
        return"quiz";
    }

    // 대단원 시험 시작
    @GetMapping("/chapter")
    public String startChapterQuiz(@RequestParam Long chapterId,
                                   @RequestParam(required = false) Integer grade,
                                   @RequestParam(required = false) String subject,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   HttpSession session, Model model) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        List<QuizDetailDto> quizList = quizService.getChapterQuiz(chapterId, accountId, session);

        QuizDetailDto currentQuiz = quizList.get(0);

        model.addAttribute("quizList", quizList);
        model.addAttribute("currentQuiz", currentQuiz);
        model.addAttribute("chapterId", chapterId);
        model.addAttribute("currentIndex", 0);
        model.addAttribute("grade", grade);
        model.addAttribute("subject", subject);

        Long lessonId = quizService.getFirstLessonIdOfChapter(chapterId);
        model.addAttribute("lessonId", lessonId);

        LessonDetailDto lessonDetail = lessonService.getLessonDetailSafe(lessonId);
        model.addAttribute("lessonTitle", lessonDetail.getTitle());



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

    @PostMapping("/exit")
    @ResponseBody
    public void exitQuiz(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {

        System.out.println("exitQuiz 호출됨");

        if(userDetails == null){
            System.out.println("사용자 정보 없다.");
            return;
        }

        Long accountId = Long.parseLong(userDetails.getUsername());
        String lessonIdStr = request.getParameter("lessonId");

        if (lessonIdStr != null) {
            Long lessonId = Long.parseLong(lessonIdStr);
            System.out.println("🔎 lessonId: " + lessonId);
            quizService.exitOngoingQuiz(accountId, lessonId);
        }else{
            System.out.println("lessonId 없다.");
        }
    }






}
