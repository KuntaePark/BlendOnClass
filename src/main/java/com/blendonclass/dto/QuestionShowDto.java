package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class QuestionShowDto {
    private Long qbId;
    private String writerName;
    private String writerEmail;
    private String title;
    private String qContext;
    private LocalDateTime qWriteTime;
    private String answerName;
    private String answerEmail;
    private String aContext;
    private String aWriteTime;
}
