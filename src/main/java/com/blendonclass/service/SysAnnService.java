package com.blendonclass.service;

/*
    시스템 공지용 서비스
 */

import com.blendonclass.dto.admin.SysAnnDetailDto;
import com.blendonclass.dto.admin.SysAnnListDto;
import com.blendonclass.entity.SystemAnnounce;
import com.blendonclass.repository.SystemAnnounceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SysAnnService {
    private final SystemAnnounceRepository systemAnnounceRepository;
    
    //시스템 공지 목록을 페이지로 반환
    public Page<SysAnnListDto> getSysAnnList(Pageable pageable) {
        List<SystemAnnounce> systemAnnounces = systemAnnounceRepository.findAllByOrderByWriteTimeDesc(pageable);
        List<SysAnnListDto> sysAnnListDtos = systemAnnounces.stream()
                .map(SysAnnListDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(sysAnnListDtos, pageable, systemAnnounces.size());
    }

    //시스템 공지 상세 표시
    public SysAnnDetailDto getSysAnnDetail(Long saId) {return null;}

    //시스템 공지 수정 업로드
    public void saveSysAnnDetail(SysAnnDetailDto sysAnnDetailDto) {}
}
