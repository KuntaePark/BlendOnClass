package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class SubmitWriteDto {
    private Long sbId;
    private Long abId;
    private Long writerId;
    private Long classroomId;
    private String context;
    private LocalDateTime writeTime;
}
