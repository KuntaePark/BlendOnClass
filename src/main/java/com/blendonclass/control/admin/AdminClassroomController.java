package com.blendonclass.control.admin;

/*
    반 관리 페이지
 */

import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.ClassroomDto;
import com.blendonclass.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminClassroomController {
    private final AuthorityService authorityService;

    @GetMapping("/admin/classroom")
    public String getClassroomMngPage(@RequestParam("page")Optional<Integer> page, Model model) {
        //예시로 1학년 1반 데이터만
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<AccountListDto> accountListDtos = authorityService.getAllAccountsOfClassroom(1, 1, pageable);
        model.addAttribute("accountListDtos", accountListDtos);
        return"admin/classroomMng";
    }

}
