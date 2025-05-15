package com.blendonclass.control;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitStudentListDto;
import com.blendonclass.service.AccountService;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.board.AssignmentBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AssignmentBoardController {
    private final AssignmentBoardService assignmentBoardService;

    private final AccountService accountService;
    private final AuthorityService authorityService;

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
    public String showAssignment(@RequestParam("abId") Long abId, @RequestParam("classroomId") Long classroomId, Model model) {
        AssignmentShowDto assignmentShowDto = assignmentBoardService.showAssignment(abId);
        model.addAttribute("assignmentShowDto", assignmentShowDto);

        //과제 제출자 목록
        List<SubmitStudentListDto> submitStudentListDtos = assignmentBoardService.getSubmitStudentList(abId, classroomId);
        model.addAttribute("submitStudentListDtos", submitStudentListDtos);

        //반 학생 목록
        return "assignment";
    }
    @PostMapping("/post/submit")
    public String submit(AssignmentShowDto assignmentShowDto, @RequestParam("afileUrl") MultipartFile multipartFile, Principal principal){
        assignmentBoardService.saveSubmit(assignmentShowDto, multipartFile, Long.parseLong(principal.getName()));
        return "assignment";
    }

    public String deleteSubmit(@RequestParam("id") Long sbId, Model model) {
        return null;
    }
    @GetMapping("/post/submit/detail")
    public String getSubmitDetail(@RequestParam("sbId") Long sbId, Model model) {
        //제출물 불러오기
        return null;
    }

}
