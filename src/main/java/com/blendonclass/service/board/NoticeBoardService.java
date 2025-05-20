package com.blendonclass.service.board;

import com.blendonclass.dto.board.NoticeShowDto;
import com.blendonclass.dto.board.NoticeWriteDto;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        // NoticeBoard 변환
        NoticeBoard noticeBoard = NoticeBoard.toNoticeBoard(noticeWriteDto, authority);
        if (fileName != null) {
            noticeBoard.setFileUrl("/uploads/" + fileName); // 사용자 접근용 경로
        }
        noticeBoardRepository.save(noticeBoard);
    }

    //공지 삭제
    public void deleteNotice(Long nbId){
        noticeBoardRepository.deleteById(nbId);
    }

    //board main용 notice 목록
    public Page<NoticeShowDto> getNoticeList(Pageable pageable, Long classroomId){
        List<NoticeShowDto> noticeList = noticeBoardRepository.findNoticeBoardByClassroomId(classroomId,pageable)
                .stream().map(NoticeShowDto::from).collect(Collectors.toList());

        return new PageImpl<>(noticeList, pageable, noticeList.size());
    }

    //notice 상세
    public NoticeShowDto getNoticeDetail(Long nbId, Long accountId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(nbId).get();
        NoticeShowDto noticeShowDto = NoticeShowDto.from(noticeBoard);
        if(noticeBoard.getAuthority().getAccount().getId().equals(accountId)){
            noticeShowDto.setIsWriter(true);
        } else {
            noticeShowDto.setIsWriter(false);
        }
        return noticeShowDto;
    }

    //수정 위한 notice 검색
    public NoticeWriteDto getNoticeWriteDto(Long nbId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(nbId).get();
        return NoticeWriteDto.from(noticeBoard);
    }
}
