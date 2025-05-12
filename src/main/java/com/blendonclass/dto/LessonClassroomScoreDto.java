package com.blendonclass.dto;

import com.blendonclass.constant.LTYPE;
import com.blendonclass.constant.SUBJECT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class LessonClassroomScoreDto {
    private Long chapId;
    private int grade;
    private SUBJECT subject;
    private String chapterTitle;
    private Long lessonId;
    private String lessonTitle;
    private LTYPE lessonType;
    private Float completeRate;
    private Float attemptCount;
}
