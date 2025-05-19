package com.blendonclass.dto;

import com.blendonclass.entity.Account;
import com.blendonclass.entity.QuestionBoard;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class QuestionShowDto {
    private Long id;
    private String writerName;
    private String writerEmail;
    private String title;
    private String qContext;
    private LocalDateTime qWriteTime;
    private String answererName;
    private String answererEmail;
    private String aContext;
    private String aWriteTime;
    private Boolean isWriter;
    private Boolean isAnswerer;

    public static QuestionShowDto from(QuestionBoard questionBoard){
        QuestionShowDto questionShowDto = new QuestionShowDto();
        Account ansAccount = questionBoard.getAccount();
        questionShowDto.setId(questionBoard.getId());
        questionShowDto.setWriterName(questionBoard.getAuthority().getAccount().getName());
        questionShowDto.setWriterEmail(questionBoard.getAuthority().getAccount().getEmail());
        questionShowDto.setTitle(questionBoard.getTitle());
        questionShowDto.setQContext(questionBoard.getQContext());
        questionShowDto.setQWriteTime(questionBoard.getQWriteTime());
        questionShowDto.setAnswererName(ansAccount != null ? ansAccount.getName() : null);
        questionShowDto.setAnswererEmail(ansAccount != null ? ansAccount.getEmail() : null);
        questionShowDto.setAContext(questionBoard.getAContext());
        questionShowDto.setAWriteTime(questionShowDto.getAWriteTime());

        questionShowDto.aContext = questionBoard.getAContext();
        return questionShowDto;
    }
}
