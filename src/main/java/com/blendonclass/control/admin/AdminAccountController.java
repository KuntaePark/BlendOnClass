package com.blendonclass.control.admin;

/*
    계정 관리 페이지
 */

import com.blendonclass.dto.admin.AccountDto;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.dto.admin.AuthReqListDto;
import com.blendonclass.service.AccountService;
import com.blendonclass.service.AuthorityService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        Pageable accPageable = PageRequest.of(accPage.orElse(0), 5);
        Page<AccountListDto> accountListDtos = accountService.searchAccountList(accPageable, accountSearchDto);

        
        //권한 요청 목록
        Pageable authReqPageable = PageRequest.of(authReqPage.orElse(0), 5);
        Page<AuthReqListDto> authReqListDtos = authorityService.getAuthReqList(authReqPageable);

        model.addAttribute("accountListDtos", accountListDtos);
        model.addAttribute("accountSearchDto", accountSearchDto);
        model.addAttribute("authReqListDtos", authReqListDtos);
        model.addAttribute("maxPage", 5);
        return "admin/accountMng";
    }

    //계정 정보 수정 페이지
    @GetMapping("/admin/modifyAccount")
    public String getAccountModifyPage(@RequestParam("id") Long accountId,
                                       Model model) {
        AccountDto accountDto = accountService.getAccountInfo(accountId);
        model.addAttribute("accountDto", accountDto);
        return "admin/accountMod";
    }
    
    //수정된 계정 정보 저장 요청
    @PostMapping("/admin/saveInfo")
    public String saveAccountInfo(AccountDto accountDto, Model model) {
        accountService.updateAccount(accountDto);
        return "redirect:/admin/accounts";
    }

    //계정 정보 삭제 요청
    @PostMapping("/admin/deleteAccount")
    public String deleteAccount(@RequestParam("id") Long accountId) {
        //accountService.deleteAccount(accountId);
        return "redirect:/admin/accounts";
    }

    //계정 생성 페이지
    @GetMapping("/admin/accountGen")
    public String createAccount(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "admin/accountGen";
    }
    
    //단일 계정 생성 요청
    @PostMapping("/admin/accountGen/single")
    public String createSingleAccount(@Valid AccountDto accountDto,
                                      BindingResult bindingResult,
                                      Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/accountGen";
        }

        accountService.saveAccount(accountDto);
        return "redirect:/admin/accountGen";
    }

    //파일 통한 계정 일괄 생성 요청
    @PostMapping("/admin/accountGen/multi")
    public String createMultipleAccount(@RequestParam("file") MultipartFile file,
                                        Model model) {
        if(file.isEmpty()) {
            model.addAttribute("fileLoadError","파일을 등록하세요.");
            model.addAttribute("accountDto", new AccountDto());
            return "admin/accountGen";
        }
        //file 서비스에 전달
        try {
            accountService.createAccountByFile(file);
        } catch(IllegalArgumentException e) {
            model.addAttribute("fileLoadError",e.getMessage());
            model.addAttribute("accountDto", new AccountDto());
            return "admin/accountGen";
        }
        return "redirect:/admin/accountGen";
    }
}
