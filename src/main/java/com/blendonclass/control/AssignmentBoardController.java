package com.blendonclass.control;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.service.AccountService;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.board.AssignmentBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        List<AccountListDto> accountListDtos = authorityService.getAllStudentsOfClassroom(classroomId); // 이 메서드는 따로 만들어야 함
        model.addAttribute("accountListDtos", accountListDtos);
        return "assignment";
    }

    public String deleteSubmit(@RequestParam("id") Long sbId, Model model) {
        return null;
    }

    public String getSubmitDetail(@RequestParam("id") Long sbId, Model model) {return null;}

}
