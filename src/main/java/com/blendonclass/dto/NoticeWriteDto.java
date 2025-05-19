package com.blendonclass.dto;

import com.blendonclass.entity.NoticeBoard;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Setter
@Getter
public class NoticeWriteDto {
    private Long nbId;
    private Long writerId;
    private Long classroomId;
    @NotBlank(message="제목을 입력하세요.")
    private String title;
    @NotBlank(message="내용을 입력하세요.")
    private String context;

    public static NoticeWriteDto from(NoticeBoard noticeBoard) {
        NoticeWriteDto noticeWriteDto = new NoticeWriteDto();
        noticeWriteDto.setNbId(noticeBoard.getId());
        noticeWriteDto.setWriterId(noticeBoard.getAuthority().getAccount().getId());
        noticeWriteDto.setClassroomId(noticeBoard.getAuthority().getClassroom().getId());
        noticeWriteDto.setTitle(noticeBoard.getTitle());
        noticeWriteDto.setContext(noticeBoard.getContext());
        return noticeWriteDto;
    }
}
