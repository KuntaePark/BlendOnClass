package com.blendonclass.control;

import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.service.board.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class QuestionBoardController {
    private final QuestionBoardService questionBoardService;

    //private final AlarmService alarmService;

    //질문 작성 페이지 요청
    @GetMapping("/question/write")
    public String getQuestionWritePage(@RequestParam("id") Long classroomId, Model model){
        QuestionWriteDto questionWriteDto = new QuestionWriteDto();
        model.addAttribute("questionWriteDto", questionWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "board/questionWrite";
    }

    //질문 작성 업로드
    @PostMapping("/question/write")
    public String saveQuestion(QuestionWriteDto questionWriteDto,
                               Principal principal,
                               @RequestParam("id") Long classroomId,
                               Model model){
        Long id = Long.parseLong(principal.getName());
        System.out.println(questionWriteDto);
        questionWriteDto.setWriterId(id);
        questionWriteDto.setClassroomId(classroomId);
        questionBoardService.saveQuestion(questionWriteDto);

        model.addAttribute("classroomId", classroomId);
        return "redirect:/board?id="+classroomId;
    }

    @PostMapping("/question/delete")
    public String deleteQuestion(@RequestParam("id") Long qbId){
        questionBoardService.deleteQuestion(qbId);
        return "redirect:/student";
    }

    //답변 삭제
    @PostMapping("/question/answer/delete")
    public String deleteAnswer(@RequestParam("id") Long qbId, Principal principal){
        Long id = Long.parseLong(principal.getName());
        questionBoardService.deleteAnswer(qbId, id);
        return "redirect:/student";
    }
    
    //질문 상세 페이지 요청
    @GetMapping("/question/detail")
    public String getQuestionDetail(@RequestParam("id") Long qbId, Principal principal, Model model){
        Long accountId = Long.parseLong(principal.getName());
        QuestionShowDto questionShowDto = questionBoardService.getQuestionDetail(qbId,accountId);
        model.addAttribute("questionShowDto", questionShowDto);
        return "board/questionDetail";
    }

    //질문 답변 저장
    @PostMapping("/question/answer/write")
    public String saveAnswer(@RequestParam("id") Long qbId, Principal prinicpal, @RequestParam String aContext){
        Long id = Long.parseLong(prinicpal.getName());
        questionBoardService.saveAnswer(qbId, id, aContext);
        return "redirect:/student";
    }
}
