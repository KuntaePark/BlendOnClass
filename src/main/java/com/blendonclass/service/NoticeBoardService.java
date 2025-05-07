package com.blendonclass.service;

import com.blendonclass.dto.NoticeShowDto;
import com.blendonclass.entity.NoticeBoard;
import com.blendonclass.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NoticeBoardService {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    public void saveNotice(NoticeBoard noticeBoard, MultipartFile multipartFile) {

    }

    public void deleteNotice(Long nbId){

    }

    public NoticeShowDto getNoticeDetail(Long nbId){
        return null;
    }

    public List<NoticeShowDto> getNoticeList(Pageable pageable, Long classroomId){
        return null;
    }
}
