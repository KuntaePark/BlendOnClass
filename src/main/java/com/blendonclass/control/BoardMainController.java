package com.blendonclass.control;

import com.blendonclass.service.AssignmentBoardService;
import com.blendonclass.service.NoticeBoardService;
import com.blendonclass.service.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardMainController {
    private final NoticeBoardService noticeBoardService;

    private final QuestionBoardService questionBoardService;

    private final AssignmentBoardService assignmentBoardService;

    public String getClassroomBoardPage(@RequestParam("id") Long classroomId, Model model){
        return null;
    }

    @GetMapping("/")
    public String home(){
        return "post";
    }
}
