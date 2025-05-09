package com.blendonclass.service.board;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.NoticeBoard;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.board.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    private final AuthorityRepository authorityRepository;

    public void saveNotice(NoticeWriteDto noticeWriteDto, MultipartFile multipartFile) {
        //계정 id와 반 id로 검색
        Authority authority = authorityRepository.findByAccountIdAndClassroomId(noticeWriteDto.getWriterId(),noticeWriteDto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("권한이 존재하지 않습니다."));

        NoticeBoard noticeBoard = NoticeBoard.toNoticeBoard(noticeWriteDto, authority);
        noticeBoardRepository.save(noticeBoard);
    }

    public void deleteNotice(Long nbId){

    }

    public NoticeShowDto getNoticeDetail(Long nbId){
        return null;
    }

    public Page<NoticeShowDto> getNoticeList(Pageable pageable, Long classroomId){
        List<NoticeShowDto> noticeList = noticeBoardRepository.findNoticeBoardByClassroomId(classroomId,pageable)
                .stream().map(NoticeShowDto::from).collect(Collectors.toList());

        return new PageImpl<>(noticeList, pageable, noticeList.size());
    }
}
