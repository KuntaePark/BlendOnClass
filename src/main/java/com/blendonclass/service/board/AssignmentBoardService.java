package com.blendonclass.service.board;

import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.AssignmentWriteDto;
import com.blendonclass.dto.SubmitShowDto;
import com.blendonclass.dto.SubmitWriteDto;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.Authority;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.board.AssignmentBoardRepository;
import com.blendonclass.repository.board.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentBoardService {
    @Autowired
    private AssignmentBoardRepository assignmentBoardRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public void saveAssignmentBoard(AssignmentWriteDto assignmentWriteDto, MultipartFile multipartFile) {
        Authority authority = authorityRepository.findByAccountIdAndClassroomId(assignmentWriteDto.getWriterId(),assignmentWriteDto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("권한이 존재하지 않습니다."));

        String fileName = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String uploadDir = "C:/assignment/"; // 예: /home/ubuntu/uploads/ 또는 C:/uploads/
                fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent()); // 폴더 없으면 생성
                multipartFile.transferTo(filePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }
        AssignmentBoard assignmentBoard = AssignmentBoard.from(assignmentWriteDto, authority);
        if (fileName != null) {
            assignmentBoard.setFileUrl("/assignment/" + fileName); // 사용자 접근용 경로
        }
        System.out.println("assignmentBoard = " + assignmentBoard);
        assignmentBoardRepository.save(assignmentBoard);
    }

    public void deleteAssignmentBoard(Long abId){

    }

    public void getAssignmentBoard(Long abId){

    }

    public AssignmentShowDto showAssignment(Long id){
        AssignmentBoard assignmentBoard = assignmentBoardRepository.findById(id).orElse(null);
        AssignmentShowDto assignmentShowDto = AssignmentShowDto.from(assignmentBoard);
        return assignmentShowDto;
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
