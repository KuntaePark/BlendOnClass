package com.blendonclass.control;

import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.service.board.AssignmentBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AssignmentBoardController {
    private final AssignmentBoardService assignmentBoardService;

    // private final AlarmService alarmService;

    public String saveAssignmentBoard(AssignmentBoard assignmentBoard, MultipartFile multipartFile, Model model) {
        return null;
    }

    public String deleteAssignment(@RequestParam("id") Long abId, Model model) {
        return null;
    }

    public String submitAssignment(SubmitWriteDto submitWriteDto, MultipartFile multipartFile, Model model) {
        return null;
    }

    public String deleteSubmit(@RequestParam("id") Long sbId, Model model) {
        return null;
    }

    public String getSubmitDetail(@RequestParam("id") Long sbId, Model model) {
        return null;
    }

    @GetMapping("/post/task")
    public String task(@RequestParam("id") Long id){
        return "task";
    }
}
