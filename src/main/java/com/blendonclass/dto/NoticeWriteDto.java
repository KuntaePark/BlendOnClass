package com.blendonclass.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Setter
@Getter
public class NoticeWriteDto {
    private Long nbId;
    private Long writerId;
    private Long classroomId;
    private String title;
    private String context;
}
