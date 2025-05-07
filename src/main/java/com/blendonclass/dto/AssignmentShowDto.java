package com.blendonclass.dto;

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
    private LocalTime dueDate;
    private String fileUrl;
}
