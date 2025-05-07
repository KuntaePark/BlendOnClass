package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class AssignmentWriteDto {
    private Long abId;
    private Long writerId;
    private String title;
    private String context;
    private LocalDateTime writeTime;
    private LocalDate dueDate;
}
