package com.blendonclass.service.board;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.dto.NoticeWriteDto;
import com.blendonclass.entity.NoticeBoard;
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

    public void saveNotice(NoticeWriteDto noticeWriteDto, MultipartFile multipartFile) {
        NoticeBoard noticeBoard = NoticeBoard.toNoticeBoard(noticeWriteDto);
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
