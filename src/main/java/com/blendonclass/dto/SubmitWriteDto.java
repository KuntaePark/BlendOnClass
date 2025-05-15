package com.blendonclass.dto;

import com.blendonclass.entity.SubmitBoard;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class SubmitWriteDto {
    private Long sbId;
    private Long abId;
    private Long writerId;
    private String context;
    private LocalDateTime writeTime;

}
