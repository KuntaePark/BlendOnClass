package com.blendonclass.dto;

import com.blendonclass.entity.Progress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter@Setter
@ToString
public class ProgressDto {
    private Long id;
    private Long startLessonId;
    private Long endLessonId;
    private LocalDate endDate;
    private Long classroomId;

    public static ProgressDto from(Progress progress) {
        ProgressDto dto = new ProgressDto();
        dto.setId(progress.getId());
        dto.setStartLessonId(progress.getStartLesson().getId());
        dto.setEndLessonId(progress.getEndLesson().getId());
        dto.setEndDate(progress.getEndDate());
        dto.setClassroomId(progress.getClassroom().getId());

        return dto;
    }
}
