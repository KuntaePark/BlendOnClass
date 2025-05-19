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
    public String getQuestionWritePage(@RequestParam(name="id", required = false) Long qbId,
                                        @RequestParam("classroomId") Long classroomId,
                                       Model model){
        QuestionWriteDto questionWriteDto = null;
        if(qbId != null){
            questionWriteDto = questionBoardService.getQuestionWriteDto(qbId);
        } else {
            questionWriteDto = new QuestionWriteDto();
        }
        System.out.println("페이지 요청 시: "+questionWriteDto);
        model.addAttribute("questionWriteDto", questionWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "board/questionWrite";
    }

    //질문 작성 업로드
    @PostMapping("/question/write")
    public String saveQuestion(QuestionWriteDto questionWriteDto,
                               Principal principal,
                               @RequestParam("classroomId") Long classroomId,
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
    public String deleteQuestion(@RequestParam("id") Long qbId, @RequestParam("classroomId") Long classroomId){
        questionBoardService.deleteQuestion(qbId);
        return "redirect:/board?id="+classroomId;
    }

    //답변 삭제
    @PostMapping("/question/answer/delete")
    public String deleteAnswer(@RequestParam("id") Long qbId,
                               @RequestParam("classroomId") Long classroomId,
                               Principal principal){
        Long id = Long.parseLong(principal.getName());
        questionBoardService.deleteAnswer(qbId, id);
        return "redirect:/board/question/detail?id="+qbId+"&classroomId="+classroomId;
    }
    
    //질문 상세 페이지 요청
    @GetMapping("/question/detail")
    public String getQuestionDetail(@RequestParam("id") Long qbId, @RequestParam("classroomId") Long classroomId, Principal principal, Model model){
        Long accountId = Long.parseLong(principal.getName());
        QuestionShowDto questionShowDto = questionBoardService.getQuestionDetail(qbId,accountId);
        model.addAttribute("questionShowDto", questionShowDto);
        model.addAttribute("classroomId", classroomId);
        return "board/questionDetail";
    }

    //질문 답변 저장
    @PostMapping("/question/answer/write")
    public String saveAnswer(@RequestParam("id") Long qbId,
                             @RequestParam("classroomId") Long classroomId,
                             Principal principal, @RequestParam String aContext){
        Long id = Long.parseLong(principal.getName());
        questionBoardService.saveAnswer(qbId, id, aContext);
        return "redirect:/board/question/detail?id="+qbId+"&classroomId="+classroomId;
    }
}
