package com.blendonclass.service;
/*
    권한 관련 서비스
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.ClassroomListDto;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AuthListDto;
import com.blendonclass.dto.admin.AuthReqListDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.AuthRequest;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthRequestRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomRepository;
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
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthRequestRepository authRequestRepository;
    private final AccountRepository accountRepository;
    private final ClassroomRepository classroomRepository;

    public List<ClassroomListDto> getClassroomsByAccountId(Long accountId) {
        List<Authority> authorities = authorityRepository.findByAccountId(accountId);
        return authorities.stream()
                .map(ClassroomListDto::fromAuthority)
                .distinct()
                .collect(Collectors.toList());
    }


    //단일 권한 추가
    public void addAuthority(Long accountId, Long classroomId, SUBJECT authType) {
        Account account = accountRepository.findById(accountId).get();
        Classroom classroom = classroomRepository.findById(classroomId).get();
        Authority authority = new Authority();

        authority.setAccount(account);
        authority.setClassroom(classroom);
        authority.setAuthType(authType);
        authorityRepository.save(authority);
    }

    //해당 권한 삭제
    public void deleteAuthority(Long authId) {}

    //권한 요청 수락
    public void acceptAuthorityRequest(Long arId, boolean accept) {}

    //권한 요청 목록 표시
    public Page<AuthReqListDto> getAuthReqList(Pageable pageable){
        List<AuthRequest> authReqList = authRequestRepository.findAllByOrderByReqTimeDesc(pageable);
        List<AuthReqListDto> authReqListDtos = authReqList.stream()
                .map(AuthReqListDto::from).collect(Collectors.toList());

        return new PageImpl<>(authReqListDtos, pageable, authReqListDtos.size());
    }

    //특정 반의 권한 보유자 모두 조회
    public List<AuthListDto> getAllAccountsOfClassroom(Long classroomId){
        List<Authority> authorities = authorityRepository.findByClassroom_Id(classroomId);
       List<AuthListDto> authListDtos = authorities.stream().map(AuthListDto::from).collect(Collectors.toList());

        return authListDtos;
    }

    public List<AccountListDto> getAllStudentsOfClassroom(Long classroommId) {
        Classroom classroom = classroomRepository.findById(classroommId).get();
        List<Authority> authorities = authorityRepository.findByClassroom(classroom);
        List<AccountListDto> accountListDtos = new ArrayList<>();
        for (Authority authority : authorities) {
            if(authority.getAccount().getRole() == ROLE.STUDENT){
                accountListDtos.add(AccountListDto.from(authority.getAccount()));
            }
        }
        return accountListDtos;
    }

    public Long getClassroomIdOfStudent(Long studentId) {
        Authority authority = authorityRepository.findByAccount_Id(studentId);
        return authority.getClassroom().getId();
    }


    public List<Classroom> getClassroomsByAccountIdAndSubjects(Long accountId, List<SUBJECT> subjects) {
        List<Authority> authorities = authorityRepository.findByAccountId(accountId);

        return authorities.stream()
                .filter(a -> subjects.contains(a.getAuthType())) // 과목 필터링
                .map(Authority::getClassroom)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Authority> getAuthoritiesByAccountId(Long accountId) {
        return authorityRepository.findByAccountId(accountId).stream()
                .filter(a -> a != null && a.getClassroom() != null) // 💥 null 방지
                .collect(Collectors.toList());
    }

}
