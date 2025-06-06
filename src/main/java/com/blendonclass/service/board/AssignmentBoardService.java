package com.blendonclass.service.board;

import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.board.*;
import com.blendonclass.entity.Account;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.SubmitBoard;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.board.AssignmentBoardRepository;
import com.blendonclass.repository.board.SubmitBoardRepository;
import com.blendonclass.service.AuthorityService;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("권한이 존재하지 않습니다.")).get(0);

        String fileName = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String uploadDir = "C:/uploads/"; // 예: /home/ubuntu/uploads/ 또는 C:/uploads/
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
            assignmentBoard.setFileUrl("/uploads/" + fileName); // 사용자 접근용 경로
        }
        assignmentBoardRepository.save(assignmentBoard);
    }

    public void deleteAssignmentBoard(Long abId){
        assignmentBoardRepository.deleteById(abId);
    }

    public AssignmentShowDto getAssignmentDetail(Long id, Long accountId){
        AssignmentBoard assignmentBoard = assignmentBoardRepository.findById(id).get();
        AssignmentShowDto assignmentShowDto = AssignmentShowDto.from(assignmentBoard);
        //writer check
        if(assignmentBoard.getAuthority().getAccount().getId().equals(accountId)){
            assignmentShowDto.setIsWriter(true);
        } else {
            assignmentShowDto.setIsWriter(false);
        }

        return assignmentShowDto;
    }

    public void saveSubmit(SubmitWriteDto submitWriteDto, MultipartFile multipartFile) {
        //id 기반 관련 entity 모두 조회
        AssignmentBoard assignmentBoard = assignmentBoardRepository.findById(submitWriteDto.getAbId()).get();
        Account account = accountRepository.findById(submitWriteDto.getWriterId()).get();
        
        //새 entity 생성
        SubmitBoard submitBoard = new SubmitBoard();
        submitBoard.setId(submitWriteDto.getSbId());
        submitBoard.setContext(submitWriteDto.getContext());
        submitBoard.setAccount(account);
        submitBoard.setAssignmentBoard(assignmentBoard);

        //파일 저장
        String fileName = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String uploadDir = "C:/uploads/"; // 예: /home/ubuntu/uploads/ 또는 C:/uploads/
                fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent()); // 폴더 없으면 생성
                multipartFile.transferTo(filePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }

        if (fileName != null) {
            submitBoard.setFileUrl("/uploads/" + fileName); // 사용자 접근용 경로
        }
        submitBoardRepository.save(submitBoard);
    }

    //과제 제출 삭제
    public void deleteSubmit(Long sbId){
        submitBoardRepository.deleteById(sbId);
    }

    public SubmitShowDto getSubmitDetail(Long sbId, Long accountId){
        SubmitBoard submitBoard = submitBoardRepository.findById(sbId).get();
        SubmitShowDto submitShowDto = SubmitShowDto.from(submitBoard);
        if(submitBoard.getAccount().getId().equals(accountId)){
            submitShowDto.setIsWriter(true);
        } else {
            submitShowDto.setIsWriter(false);
        }
        return submitShowDto;
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
        for(SubmitStudentListDto submitStudentListDto : submitStudentListDtos){
            System.out.println(submitStudentListDto);
        }
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
                submitStudentListDtos.add(new SubmitStudentListDto(account.getId(), abId, null, account.getName(), account.getEmail(), false));
            }
        }
        //필요할 경우 이름으로 정렬
        return submitStudentListDtos;
    }

    public SubmitWriteDto getSubmitWriteDto(Long sbId) {
        return SubmitWriteDto.fromSubmitBoard(submitBoardRepository.findById(sbId).get());
    }
    public Long findSbByAccountIdAndAbId(Long accountId, Long abId) {
       SubmitBoard submitBoard = submitBoardRepository.findByAccount_IdAndAssignmentBoard_Id(accountId,abId);
       if(submitBoard != null) {
           return submitBoard.getId();
       } else {
           return null;
       }
    }

    public AssignmentWriteDto getAssignmentWriteDto(Long abId) {
        return AssignmentWriteDto.from(assignmentBoardRepository.findById(abId).get());
    }
}
