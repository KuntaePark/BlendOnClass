package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class NoticeShowDto {
    private Long nbId;
    private String writerName;
    private String writerEmail;
    private String title;
    private String context;
    private LocalDateTime writeTime;
    private String fileUrl;
}
