package com.blendonclass.service;

/*
    관리자용 계정 관리 관련 서비스
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.dto.admin.AccountDto;
import com.blendonclass.dto.admin.AccountGeneratedDto;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.entity.Account;
import com.blendonclass.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    //계정 목록 검색 및 반환
    public Page<AccountListDto> searchAccountList(Pageable pageable, AccountSearchDto accountSearchDto) {
        //todo
        //검색
        List<Account> accounts = null;
        String keyword = accountSearchDto.getKeyword();
        ROLE role = accountSearchDto.getRoleType();
        System.out.println(role + " " + keyword);
        if(role != null) {
            accounts = accountRepository.findByRoleAndNameContaining(role, keyword, pageable);
        } else {
            accounts = accountRepository.findByNameContaining(keyword, pageable);
        }

        List<AccountListDto> accountListDtos = accounts.stream()
                .map(AccountListDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(accountListDtos, pageable, accounts.size());
    }

    //단일 계정 생성, 생성 성공 시 로그인 아이디 및 비밀번호 반환
    public AccountGeneratedDto saveAccount(AccountDto accountDto) {
        return null;
    }

    //단일 계정 수정
    public void updateAccount(AccountDto accountDto) {

    }

    //엑셀 파일로 계정 일괄 생성
    public String createAccountByFile(MultipartFile file) {
        return "";
    }

    //계정 삭제
    public void deleteAccount(Long accountId) {

    }

}
