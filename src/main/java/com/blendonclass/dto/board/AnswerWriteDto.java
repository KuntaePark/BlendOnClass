package com.blendonclass.dto.board;

import com.blendonclass.entity.QuestionBoard;
import lombok.*;

@Getter
@Setter
public class AnswerWriteDto {
    private Long id;
    private String answerId;
    private String aContext;
    private String writeTime;

    public static AnswerWriteDto from(QuestionBoard questionBoard) {
        AnswerWriteDto answerWriteDto = new AnswerWriteDto();
        answerWriteDto.setId(questionBoard.getId());
        answerWriteDto.setAContext(questionBoard.getAContext());
        return answerWriteDto;
    }


}
