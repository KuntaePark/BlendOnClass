package com.blendonclass.control.admin;

/*
    계정 관리 페이지
 */

import com.blendonclass.dto.admin.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAccountController {
    private final AccountService accountService;
    private final AuthorityService authorityService;

    //계정 관리 페이지 요청, 관리자 메인 페이지
    @GetMapping("/accounts")
    public String getAccountMngPage(Model model) {
        //계정 목록

        AccountSearchDto accountSearchDto = new AccountSearchDto();
        accountSearchDto.setPageNum(0);
        Page<AccountListDto> accountListDtos = accountService.searchAccountList(accountSearchDto);


        model.addAttribute("accountListDtos", accountListDtos);
        model.addAttribute("accountSearchDto", accountSearchDto);
        model.addAttribute("maxPage", 5);
        return "admin/accountMng";
    }

    @GetMapping("/accounts/search")
    @ResponseBody
    public Page<AccountListDto> searchAccountList(AccountSearchDto accountSearchDto) {
        System.out.println("request search");
        return accountService.searchAccountList(accountSearchDto);

    }

    //계정 정보 수정 페이지
    @GetMapping("/modifyAccount")
    public String getAccountModifyPage(@RequestParam("id") Long accountId,
                                       Model model) {
        AccountDto accountDto = accountService.getAccountInfo(accountId);
        model.addAttribute("accountDto", accountDto);
        return "admin/accountMod";
    }
    
    //수정된 계정 정보 저장 요청
    @PostMapping("/saveInfo")
    public String saveAccountInfo(@Valid AccountDto accountDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/accountMod";
        }
        accountService.updateAccount(accountDto);
        return "redirect:/admin/accounts";
    }

    //계정 정보 삭제 요청
    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam("id") Long accountId) {
        //accountService.deleteAccount(accountId);
        return "redirect:/admin/accounts";
    }

    //계정 생성 페이지
    @GetMapping("/accountGen")
    public String createAccount(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "admin/accountGen";
    }
    
    //단일 계정 생성 요청
    @PostMapping("/accountGen/single")
    public String createSingleAccount(@Valid AccountDto accountDto,
                                      BindingResult bindingResult,
                                      Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("genType","single");
            return "admin/accountGen";
        }

        AccountGeneratedDto accountGeneratedDto = accountService.saveAccount(accountDto);
        model.addAttribute("accountGeneratedDto", accountGeneratedDto);
        model.addAttribute("accountDto", accountDto);
        model.addAttribute("genType","single");
        return "admin/accountGen";
    }

    //파일 통한 계정 일괄 생성 요청
    @PostMapping("/accountGen/multi")
    public String createMultipleAccount(@RequestParam("file") MultipartFile file,
                                        Model model) {
        if(file.isEmpty()) {
            model.addAttribute("fileLoadError","파일을 등록하세요.");
            model.addAttribute("accountDto", new AccountDto());
            model.addAttribute("genType","multi");
            return "admin/accountGen";
        }
        //file 서비스에 전달
        String downloadUrl = null;
        try {
            downloadUrl = accountService.createAccountByFile(file);

        } catch(IllegalArgumentException e) {
            model.addAttribute("fileLoadError",e.getMessage());
            model.addAttribute("accountDto", new AccountDto());
            model.addAttribute("genType","multi");
            return "admin/accountGen";
        }

        model.addAttribute("accountDto", new AccountDto());
        model.addAttribute("genType","multi");
        model.addAttribute("downloadUrl", downloadUrl);
        return "admin/accountGen";
    }
}
