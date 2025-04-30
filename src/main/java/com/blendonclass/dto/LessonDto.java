package com.blendonclass.dto;

import com.blendonclass.entity.Lesson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;


@Getter @Setter
public class LessonDto {
    private Long lessonId;
    private String lessonTitle;
    private String lessonBrief;
    private String lessonType;
    private int completeRate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static LessonDto from(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }
}
