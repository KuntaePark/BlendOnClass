package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class QuestionWriteDto {
    private Long qbId;
    private Long writerId;
    private String title;
    private String qContext;
    private LocalDateTime qWriteTime;
}
