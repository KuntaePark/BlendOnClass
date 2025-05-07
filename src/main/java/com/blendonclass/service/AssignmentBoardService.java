package com.blendonclass.service;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitShowDto;
import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AssignmentBoardService {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    public void saveAssignmentBoard(AssignmentWriteDto assignmentWriteDto, MultipartFile multipartFile) {

    }

    public void deleteAssignmentBoard(Long abId){

    }

    public void getAssignmentBoard(Long abId){

    }

    public AssignmentWriteDto getAssignmentBoardDto(Long abId){
        return null;
    }

    public void saveSubmit(SubmitWriteDto submitWriteDto, MultipartFile multipartFile) {

    }

    public void deleteSubmit(Long sbId){

    }

    public SubmitShowDto getSubmitDetail(Long sbId){
        return null;
    }

    public List<AssignmentShowDto> getAssignmentList(Pageable pageable, Long classroomId){
        return null;
    }
}
