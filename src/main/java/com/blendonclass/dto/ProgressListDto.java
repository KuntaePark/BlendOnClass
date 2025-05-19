package com.blendonclass.dto;


import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Progress;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class ProgressListDto {
    private Long progressId;
    private String startTitle;
    private String endTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private float completeRate;
    private SUBJECT subject;

    public ProgressListDto from(Progress progress, float completeRate) {
        ProgressListDto progressListDto = new ProgressListDto();

        progressListDto.setProgressId(progress.getId());
        progressListDto.setStartTitle(progress.getStartLesson().getLessonTitle());
        progressListDto.setEndTitle(progress.getEndLesson().getLessonTitle());
        progressListDto.setStartDate(progress.getStartDate());
        progressListDto.setEndDate(progress.getEndDate());
        progressListDto.setSubject(progress.getStartLesson().getChapter().getSubject());
        progressListDto.setCompleteRate(completeRate);

        return progressListDto;

    }
}
