package com.blendonclass.dto;

import com.blendonclass.constant.LTYPE;
import com.blendonclass.constant.SUBJECT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class LessonScoreDto {
    private Long chapId;
    private int grade;
    private SUBJECT subject;
    private String chapterTitle;
    private Long lessonId;
    private String lessonTitle;
    private LTYPE lessonType;
    private float completeRate;
    private float attemptCount;


}
