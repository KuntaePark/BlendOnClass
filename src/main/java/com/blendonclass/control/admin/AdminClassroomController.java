package com.blendonclass.control.admin;

/*
    반 관리 페이지
 */

import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.dto.admin.ClassroomGenRequest;
import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.ClassroomRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminClassroomController {
    private final ClassroomService classroomService;
    private final AuthorityService authorityService;
    private final AccountService accountService;

    @GetMapping("/admin/classroom")
    public String getClassroomMngPage(AccountSearchDto accountSearchDto,
                                      @RequestParam("accPage") Optional<Integer> accPage,
                                      @RequestParam(name = "id", required = false) Long classroomId, Model model) {
        List<ClassroomDto> classroomDtos = classroomService.findAll();
        model.addAttribute("classroomDtos", classroomDtos);

        List<AccountListDto> accountListDtos = null;
        if(classroomId != null) {
            accountListDtos = authorityService.getAllAccountsOfClassroom(classroomId);
            model.addAttribute("accountListDtos", accountListDtos);
        }

        Pageable accPageable = PageRequest.of(accPage.orElse(0), 10);
        Page<AccountListDto> allAccountListDtos = accountService.searchAccountList(accPageable, accountSearchDto);
        model.addAttribute("allAccountListDtos", allAccountListDtos);
        return"admin/classroomMng";
    }

    @PostMapping("admin/classroom/add")
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
}
