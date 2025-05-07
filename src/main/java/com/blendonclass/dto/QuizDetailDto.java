package com.blendonclass.dto;

import com.blendonclass.constant.QTYPE;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuizDetailDto {
    private Long quizId;

    private QTYPE quizType;

    private String contextJson;
}
