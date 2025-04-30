package com.blendonclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LessonDto {
    private Long lessonId;
    private String lessonTitle;
    private String lessonBrief;
    private String lessonType;
    private int completeRate;
}
