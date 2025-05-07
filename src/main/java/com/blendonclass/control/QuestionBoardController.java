package com.blendonclass.control;

import com.blendonclass.dto.AnswerWriteDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.service.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

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

}
