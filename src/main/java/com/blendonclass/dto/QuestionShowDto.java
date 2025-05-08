package com.blendonclass.dto;

import com.blendonclass.entity.QuestionBoard;
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

    public static QuestionShowDto from(QuestionBoard questionBoard){
        QuestionShowDto questionShowDto = new QuestionShowDto();
        questionShowDto.qbId = questionBoard.getId();
        questionShowDto.title = questionBoard.getTitle();
        questionShowDto.qWriteTime = questionBoard.getQWriteTime();
        questionShowDto.qContext = questionBoard.getQContext();
        questionShowDto.aContext = questionBoard.getAContext();
        return questionShowDto;
    }
}
