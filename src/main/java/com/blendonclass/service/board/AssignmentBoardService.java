package com.blendonclass.service.board;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitShowDto;
import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.repository.board.AssignmentBoardRepository;
import com.blendonclass.repository.board.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentBoardService {
    @Autowired
    private AssignmentBoardRepository assignmentBoardRepository;

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

    public Page<AssignmentShowDto> getAssignmentList(Pageable pageable, Long classroomId){
        //todo - 여기서 repository 이용하여 데이터 가져오기
        List<AssignmentShowDto> assignmentList = assignmentBoardRepository.findAssignmentBoardByClassroomId(classroomId, pageable)
                .stream().map(AssignmentShowDto::from).collect(Collectors.toList());

        return new PageImpl<>(assignmentList, pageable, assignmentList.size());
    }
}
