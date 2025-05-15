package com.blendonclass.dto;

import com.blendonclass.entity.QuestionBoard;
import lombok.*;

@Getter
@Setter
@ToString
public class QuestionWriteDto {
    private Long id;
    private Long writerId;
    private Long classroomId;
    private String title;
    private String qContext;

    public static QuestionWriteDto toQuestionWriteDto(QuestionBoard questionBoard) {
        QuestionWriteDto questionWriteDto = new QuestionWriteDto();
        //questionWriteDto.setId(questionBoard.getId());
        questionWriteDto.setTitle(questionBoard.getTitle());
        questionWriteDto.setQContext(questionBoard.getQContext());
        return questionWriteDto;
    }
}
