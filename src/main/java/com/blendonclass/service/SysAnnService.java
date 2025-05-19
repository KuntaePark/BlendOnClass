package com.blendonclass.service;

/*
    시스템 공지용 서비스
 */

import com.blendonclass.dto.admin.SysAnnDetailDto;
import com.blendonclass.dto.admin.SysAnnListDto;
import com.blendonclass.entity.SystemAnnounce;
import com.blendonclass.repository.SystemAnnounceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SysAnnService {
    private final SystemAnnounceRepository systemAnnounceRepository;
    
    //시스템 공지 목록을 페이지로 반환
    public Page<SysAnnListDto> getSysAnnList(Pageable pageable) {
        Page<SystemAnnounce> systemAnnounces = systemAnnounceRepository.findAllByOrderByWriteTimeDesc(pageable);
        return systemAnnounces.map(SysAnnListDto::from);
    }

    //시스템 공지 상세 표시
    public SysAnnDetailDto getSysAnnDetail(Long saId) {
        return SysAnnDetailDto.from(systemAnnounceRepository.findById(saId).get());
    }

    //시스템 공지 수정 업로드
    public void saveSysAnnDetail(SysAnnDetailDto sysAnnDetailDto) {
        systemAnnounceRepository.save(sysAnnDetailDto.to());
    }

    //시스템 공지 삭제
    public void deleteAnnounce(Long saId) {
        systemAnnounceRepository.deleteById(saId);
    }
}
