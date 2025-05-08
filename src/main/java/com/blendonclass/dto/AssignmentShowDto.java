package com.blendonclass.dto;

import com.blendonclass.entity.AssignmentBoard;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AssignmentShowDto {
    private Long abId;
    private String writerName;
    private String writerEmail;
    private String title;
    private String context;
    private LocalDateTime writeTime;
    private LocalDate dueDate;
    private String fileUrl;

    public static AssignmentShowDto from(AssignmentBoard assignmentBoard) {
        AssignmentShowDto assignmentShowDto = new AssignmentShowDto();
        assignmentShowDto.setAbId(assignmentBoard.getId());
        assignmentShowDto.setTitle(assignmentBoard.getTitle());
        assignmentShowDto.setContext(assignmentBoard.getContext());
        assignmentShowDto.setWriteTime(assignmentBoard.getWriteTime());
        assignmentShowDto.setFileUrl(assignmentBoard.getFileUrl());
        assignmentShowDto.setDueDate(assignmentBoard.getDueDate());
        return assignmentShowDto;
    }
}
