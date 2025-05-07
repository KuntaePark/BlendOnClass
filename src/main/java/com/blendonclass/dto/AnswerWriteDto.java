package com.blendonclass.dto;

import lombok.*;

@Getter
@Setter
public class AnswerWriteDto {
    private Long qbId;
    private String answerId;
    private String context;
    private String writeTime;
}
