package com.blendonclass.service.board;

import com.blendonclass.dto.*;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.SubmitBoard;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.board.AssignmentBoardRepository;
import com.blendonclass.repository.board.NoticeBoardRepository;
import com.blendonclass.repository.board.SubmitBoardRepository;
import com.blendonclass.service.AuthorityService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AssignmentBoardService {
    private final AssignmentBoardRepository assignmentBoardRepository;

    private final AuthorityRepository authorityRepository;
    private final SubmitBoardRepository submitBoardRepository;

    private final AuthorityService authorityService;
    private final AccountRepository accountRepository;

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

    public void saveSubmit(SubmitWriteDto submitWriteDto, SubmitStudentListDto submitStudentListDto, MultipartFile multipartFile) {
        Account account = accountRepository.findById(submitStudentListDto.getAccountId())
                .orElseThrow(()-> new IllegalStateException("해당 계정이 존재하지 않습니다. id=" + submitStudentListDto.getAccountId()));
        AssignmentBoard assignmentBoard = assignmentBoardRepository.findById(submitStudentListDto.getAbId())
                .orElseThrow(()-> new IllegalStateException("해당 과제가 존재하지 않습니다. id=" + submitStudentListDto.getAbId()));
        SubmitBoard submitBoard = SubmitBoard.from(submitWriteDto, account, assignmentBoard);
        submitBoardRepository.save(submitBoard);
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
    
    //해당 반 해당과제의 제출자 목록
    public List<SubmitStudentListDto> getSubmitStudentList(Long abId, Long classroomId) {
        //존재하는 제출자 목록
        List<SubmitStudentListDto> submitStudentListDtos = submitBoardRepository.findSubmitBoardByAbIdAndClassroomId(abId, classroomId);

        //반 학생 불러오기
        List<AccountListDto> accountListDtos = authorityService.getAllStudentsOfClassroom(classroomId);
        for(AccountListDto account : accountListDtos) {
            //제출물이 존재하지 않는다면 새로 생성
            boolean exists = false;
            for(SubmitStudentListDto submitStudentListDto : submitStudentListDtos) {
                if(submitStudentListDto.getAccountId() == account.getId()) {
                    exists = true;
                    break;
                }
            }
            if(exists) {
                continue;
            } else {
                submitStudentListDtos.add(new SubmitStudentListDto(account.getId(), abId, account.getName(), account.getEmail(), false));
            }
        }
        //필요할 경우 이름으로 정렬
        return submitStudentListDtos;
    }

}
