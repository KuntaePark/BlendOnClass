package com.blendonclass.dto;

import com.blendonclass.entity.Lesson;
import com.blendonclass.entity.LessonDetail;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LessonDetailDto {
    private Long ldId;
    private Long lessonId;
    private String title;
    private String context;
    private String mediaUrl;

    public static LessonDetailDto from(Lesson lesson, LessonDetail detail){
        LessonDetailDto dto = new LessonDetailDto();
        dto.setLdId(detail.getId());
        dto.setLessonId(lesson.getId());
        dto.setTitle(lesson.getLessonTitle());
        dto.setContext(detail.getContext());
        dto.setMediaUrl(detail.getMediaUrl());
        return dto;
    }
}
