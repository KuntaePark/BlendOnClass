package com.blendonclass.service;
/*
    권한 관련 서비스
 */

import com.blendonclass.constant.SUBJECT;
import com.blendonclass.dto.admin.AuthReqListDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.AuthRequest;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.Classroom;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthRequestRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthRequestRepository authRequestRepository;
    private final AccountRepository accountRepository;
    private final ClassroomRepository classroomRepository;

    //권한 목록을 엑셀 파일로 받아 일괄 생성
    public void addAuthorityByFile(MultipartFile file) {}

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
        List<AuthReqListDto> authReqListDtos = new ArrayList<>();
        for(AuthRequest authReq : authReqList) {
            authReqListDtos.add(AuthReqListDto.from(authReq));
        }
        return new PageImpl<>(authReqListDtos, pageable, authReqListDtos.size());
    }
    
}
