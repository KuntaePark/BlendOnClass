package com.blendonclass.control;

import com.blendonclass.constant.ROLE;
import com.blendonclass.dto.board.*;
import com.blendonclass.service.AccountService;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.CustomUserDetails;
import com.blendonclass.service.board.AssignmentBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class AssignmentBoardController {
    private final AssignmentBoardService assignmentBoardService;

    private final AccountService accountService;
    private final AuthorityService authorityService;

    // private final AlarmService alarmService;
    public String deleteAssignment(@RequestParam("id") Long abId, Model model) {
        return null;
    }

    //과제 작성 페이지 요청
    @GetMapping("/assignment/write")
    public String task(@RequestParam(name = "id", required = false) Long abId,
                       @RequestParam("classroomId") Long classroomId,
                       Model model) {
        AssignmentWriteDto assignmentWriteDto = new AssignmentWriteDto();
        if(abId != null) {
            assignmentWriteDto = assignmentBoardService.getAssignmentWriteDto(abId);
        }
        model.addAttribute("assignmentWriteDto", assignmentWriteDto);
        model.addAttribute("classroomId", classroomId);
        return "board/assignmentWrite";
    }

    //작성 과제 저장
    @PostMapping("/assignment/write")
    public String submitAssignment(@RequestParam("id") Long classroomId,
                                   Principal principal,
                                   AssignmentWriteDto assignmentWriteDto,
                                   @RequestParam("file") MultipartFile multipartFile) {
        Long id = Long.parseLong(principal.getName());
        assignmentWriteDto.setWriterId(id);
        assignmentWriteDto.setClassroomId(classroomId);
        assignmentBoardService.saveAssignmentBoard(assignmentWriteDto, multipartFile);
        return "redirect:/board?id=" + classroomId;
    }

    //과제 상세 페이지 요청
    @GetMapping("/assignment/detail")
    public String getAssignmentDetail(@RequestParam("abId") Long abId,
                                      @RequestParam("classroomId") Long classroomId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails,
                                      Model model) {
        Long accountId = Long.parseLong(userDetails.getUsername());
        AssignmentShowDto assignmentShowDto = assignmentBoardService.getAssignmentDetail(abId, accountId);
        model.addAttribute("assignmentShowDto", assignmentShowDto);

        //과제 제출자 목록
        List<SubmitStudentListDto> submitStudentListDtos = assignmentBoardService.getSubmitStudentList(abId, classroomId);
        model.addAttribute("submitStudentListDtos", submitStudentListDtos);
        model.addAttribute("classroomId", classroomId);

        //학생일 경우, 제출물 있을 시 제출 완료 확인
        ROLE role = userDetails.getRole();
        if(role == ROLE.STUDENT) {
            for(SubmitStudentListDto submitStudentListDto : submitStudentListDtos) {
                if(accountId.equals(submitStudentListDto.getAccountId()) && submitStudentListDto.getIsSubmit()) {
                    model.addAttribute("isSubmit",true);
                    break;
                }
            }
        }
        return "board/assignmentDetail";
    }

    //과제 제출 페이지 요청
    @GetMapping("/assignment/submit/write")
    public String getSubmitPage(@RequestParam("id") Long abId,
                                @RequestParam(name="sbId", required = false) Long sbId,
                                @RequestParam("classroomId") Long classroomId,
                                Principal principal,
                                Model model) {

        SubmitWriteDto submitWriteDto = null;
        if(sbId != null) {
            submitWriteDto = assignmentBoardService.getSubmitWriteDto(sbId);
        } else {
            submitWriteDto = new SubmitWriteDto();
        }
        
        //과제 제출 있는 경우에도 로드
        Long accountId = Long.parseLong(principal.getName());
        Long sbId2 = assignmentBoardService.findSbByAccountIdAndAbId(accountId,abId);
        if(sbId2 != null) {
            submitWriteDto = assignmentBoardService.getSubmitWriteDto(sbId2);
        }
        model.addAttribute("abId", abId);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("submitWriteDto", submitWriteDto);
        return "board/submitWrite";
    }

    //과제 글 삭제
    @PostMapping("/assignment/delete")
    public String deleteAssignment(@RequestParam("id") Long abId, @RequestParam("classroomId") Long classroomId, Model model) {
        assignmentBoardService.deleteAssignmentBoard(abId);
        return "redirect:/board?id=" + classroomId;
    }

    //과제 제출 저장
    @PostMapping("/assignment/submit/write")
    public String submit(@RequestParam("id") Long abId,
                         SubmitWriteDto submitWriteDto,
                         MultipartFile file,
                         Principal principal, Model model){
        Long id = Long.parseLong(principal.getName());
        Long classroomId = authorityService.getClassroomIdOfStudent(id);
        submitWriteDto.setWriterId(id);
        submitWriteDto.setAbId(abId);
        System.out.println(submitWriteDto);
        assignmentBoardService.saveSubmit(submitWriteDto, file);

        return "redirect:/board/assignment/detail?abId="+abId+"&classroomId="+classroomId;
    }

    @PostMapping("/assignment/submit/delete")
    public String deleteSubmit(@RequestParam("id") Long sbId,
                               @RequestParam("abId") Long abId,
                               @RequestParam("classroomId") Long classroomId,
                               Model model) {
        assignmentBoardService.deleteSubmit(sbId);
        return "redirect:/board/assignment/detail?abId="+abId+"&classroomId="+classroomId;
    }

    //과제 제출물 상세 불러오기
    @GetMapping("/assignment/submit/detail")
    public String getSubmitDetail(@RequestParam("sbId") Long sbId, Principal principal, Model model) {
        //제출물 불러오기
        Long id = Long.parseLong(principal.getName());
        SubmitShowDto submitShowDto = assignmentBoardService.getSubmitDetail(sbId, id);
        model.addAttribute("submitShowDto", submitShowDto);
        return "board/submitDetail";
    }

}
