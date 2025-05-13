package com.blendonclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuizAnsweredDto {
    private Long quizId;
    private String studentAnswer;
}
