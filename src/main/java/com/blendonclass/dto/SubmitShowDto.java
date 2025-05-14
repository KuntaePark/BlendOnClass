package com.blendonclass.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class SubmitShowDto {
    private Long sbId;
    private Long abId;
    private String writerName;
    private String writerEmail;
    private String context;
    private LocalDateTime writeTime;
    private String fileUrl;
}
