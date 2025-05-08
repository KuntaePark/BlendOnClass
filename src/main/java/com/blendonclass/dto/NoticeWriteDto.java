package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
public class NoticeWriteDto {
    private Long nbId;
    private Long writerId;
    private String title;
    private String context;
    private LocalDateTime writeTime;
}
