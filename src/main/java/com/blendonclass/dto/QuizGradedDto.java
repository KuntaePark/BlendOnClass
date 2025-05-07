package com.blendonclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuizGradedDto {
    private Long quizId;
    private boolean isCorrect;
    private String answer;
    private String solution;
}
