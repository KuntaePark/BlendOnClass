package com.blendonclass.control.admin;

/*
    계정 관리 페이지
 */

import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.dto.admin.AuthReqListDto;
import com.blendonclass.service.AccountService;
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
public class AdminAccountController {
    private final AccountService accountService;
    private final AuthorityService authorityService;

    //계정 관리 페이지 요청, 관리자 메인 페이지
    @GetMapping("/admin/accounts")
    public String getAccountMngPage(AccountSearchDto accountSearchDto,
                                    @RequestParam("accPage") Optional<Integer> accPage,
                                    @RequestParam("authReqPage") Optional<Integer> authReqPage,
                                    Model model) {
        //계정 목록
        Pageable accPageable = PageRequest.of(accPage.orElse(0), 10);
        Page<AccountListDto> accountListDtos = accountService.searchAccountList(accPageable, accountSearchDto);
        
        //권한 요청 목록
        Pageable authReqPageable = PageRequest.of(authReqPage.orElse(0), 10);
        Page<AuthReqListDto> authReqListDtos = authorityService.getAuthReqList(authReqPageable);

        model.addAttribute("accountListDtos", accountListDtos);
        model.addAttribute("accountSearchDto", accountSearchDto);
        model.addAttribute("authReqListDtos", authReqListDtos);
        return "admin/accountMng";
    }
}
