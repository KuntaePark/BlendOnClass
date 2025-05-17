package com.blendonclass.control.admin;

/*
    반 관리 페이지
 */

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.dto.admin.AuthListDto;
import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.service.AccountService;
import com.blendonclass.service.AuthorityService;
import com.blendonclass.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminClassroomController {
    private final ClassroomService classroomService;
    private final AuthorityService authorityService;
    private final AccountService accountService;

    //반 관리 페이지 요청
    @GetMapping("/classroom")
    public String getClassroomMngPage(@RequestParam(name = "id", required = false) Long classroomId, Model model) {
        List<ClassroomDto> classroomDtos = classroomService.findAll();
        model.addAttribute("classroomDtos", classroomDtos);

        List<AuthListDto> authListDtos = null;
        if(classroomId != null) {
            //해당 반의 권한 목록 표시
            authListDtos = authorityService.getAllAccountsOfClassroom(classroomId);
            model.addAttribute("authListDtos", authListDtos);
        }
        model.addAttribute("classroomId", classroomId);
        return"admin/classroomMng";
    }

    //반 추가 요청
    @PostMapping("/classroom/add")
    public ResponseEntity<String> addClassroom(@RequestBody List<ClassroomDto> classroomDtos) {
        for(ClassroomDto classroomDto : classroomDtos) {
            System.out.println(classroomDto.getGrade()+ " " + classroomDto.getClassroomNum());
        }
        try {
            classroomService.addClassroom(classroomDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    //권한 삭제 요청
    @PostMapping("/classroom/deleteAuth")
    public ResponseEntity<String> deleteAuth(@RequestParam("id") Long authId, Model model) {
        authorityService.deleteAuthority(authId);
        return ResponseEntity.ok("success");
    }

    //권한 추가 페이지 요청
    @GetMapping("/classroom/authAdd")
    public String getAuthAddPage(@RequestParam("id") Long classroomId,
                                 Model model) {
        ClassroomDto classroomDto = classroomService.getClassroomById(classroomId);
        model.addAttribute("classroomDto", classroomDto);

        //계정 목록
        AccountSearchDto accountSearchDto = new AccountSearchDto();
        accountSearchDto.setPageNum(0);
        Page<AccountListDto> accountListDtos = accountService.searchAccountList(accountSearchDto);
        model.addAttribute("accountListDtos", accountListDtos);
        return "admin/authAdd";
    }

    //권한 추가 요청
    @PostMapping("/classroom/authAdd")
    public String addAuthority(Model model) {
        return "redirect:/admin/classroom";
    }

}
