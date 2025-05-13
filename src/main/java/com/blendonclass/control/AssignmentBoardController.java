package com.blendonclass.control;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.service.board.AssignmentBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AssignmentBoardController {
    private final AssignmentBoardService assignmentBoardService;

    // private final AlarmService alarmService;
    public String deleteAssignment(@RequestParam("id") Long abId, Model model) {
        return null;
    }

    @GetMapping("/post/task")
    public String task(@RequestParam("id") Long classroomId, Model model) {
        AssignmentWriteDto assignmentWriteDto = new AssignmentWriteDto();
        model.addAttribute("assignmentWriteDto", assignmentWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "task";
    }
    @PostMapping("/post/task")
    public String submitAssignment(@RequestParam("id") Long classroomId,
                                   Principal principal,
                                   @ModelAttribute AssignmentWriteDto assignmentWriteDto,
                                   @RequestParam("fileUrl") MultipartFile multipartFile) {
        Long id = Long.parseLong(principal.getName());
        assignmentWriteDto.setWriterId(id);
        assignmentWriteDto.setClassroomId(classroomId);
        assignmentBoardService.saveAssignmentBoard(assignmentWriteDto, multipartFile);
        return "redirect:/student";
    }
    @GetMapping("post/task/detail")
    public String showAssignment(@RequestParam("id") Long id, AssignmentShowDto assignmentShowDto, Model model) {
        assignmentShowDto = assignmentBoardService.showAssignment(id);
        model.addAttribute("assignmentShowDto", assignmentShowDto);
        return "assignment";
    }

    public String deleteSubmit(@RequestParam("id") Long sbId, Model model) {
        return null;
    }

    public String getSubmitDetail(@RequestParam("id") Long sbId, Model model) {
        return null;
    }

}
