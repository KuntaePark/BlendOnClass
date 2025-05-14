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
    private Long chapterId;
    private Integer grade;
    private String subject;

    private static ModelMapper modelMapper = new ModelMapper();

    public static LessonDto from(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }

    public static LessonDto from(Lesson lesson, int completeRate) {
        LessonDto dto = new LessonDto();
        dto.setLessonId(lesson.getId());
        dto.setLessonTitle(lesson.getLessonTitle()); // 정확히 일치
        dto.setLessonBrief(lesson.getLessonBrief());
        dto.setLessonType(lesson.getLessonType().toString()); // Enum -> String 처리
        dto.setChapterId(lesson.getChapter().getId());
        dto.setCompleteRate(completeRate);

        dto.setGrade(lesson.getChapter().getGrade());
        dto.setSubject(lesson.getChapter().getSubject().toString());
        return dto;
    }
}
