package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class AssignmentWriteDto {
    private Long id;
    private Long writerId;
    private String title;
    private Long classroomId;
    private String context;
    private LocalDate dueDate;
}
