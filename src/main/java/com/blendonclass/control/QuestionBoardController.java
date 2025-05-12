package com.blendonclass.control;

import com.blendonclass.dto.AnswerWriteDto;
import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.service.board.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class QuestionBoardController {
    @Autowired
    private final QuestionBoardService questionBoardService;

    //private final AlarmService alarmService;

    public String saveQuestion(QuestionWriteDto questionDto, Model model){
        return null;
    }

    public String deleteQuestion(@RequestParam("id") Long qbId, Model model){
        return null;
    }

    public String saveAnswer(AnswerWriteDto answerWriteDto, Model model){
        return null;
    }

    public String deleteAnswer(@RequestParam("id") Long qbId, Model model){
        return null;
    }

    public String getQuestionDetail(@RequestParam("id") Long qbId, Model model){
        return null;
    }

    @GetMapping("/post/question")
    public String question(@RequestParam("id") Long classroomId, Model model){
        QuestionWriteDto questionWriteDto = new QuestionWriteDto();
        model.addAttribute("questionWriteDto", questionWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "question";
    }
    @PostMapping("/post/question")
    public String questionSave(QuestionWriteDto questionWriteDto,
                               Principal principal,
                               @RequestParam("id") Long classroomId){
        Long id = Long.parseLong(principal.getName());
        System.out.println(questionWriteDto);
        questionWriteDto.setWriterId(id);
        questionWriteDto.setClassroomId(classroomId);
        questionBoardService.saveQuestion(questionWriteDto);
        return "redirect:/student";
    }
    @GetMapping("/post/answer")
    public String answer(@RequestParam("id") Long qbId, Model model){
        QuestionShowDto questionShowDto = questionBoardService.getQuestionDetail(qbId);
        model.addAttribute("questionShowDto", questionShowDto);
        return "answer";
    }
    @PostMapping("/post/answer")
    public String answerSave(@RequestParam("id") Long qbId, Principal prinicpal, @RequestParam String aContext){
        Long id = Long.parseLong(prinicpal.getName());
        questionBoardService.saveAnswer(qbId, id, aContext);
        return "redirect:/student";
    }
}
