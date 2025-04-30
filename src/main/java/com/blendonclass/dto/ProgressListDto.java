package com.blendonclass.dto;


import com.blendonclass.constant.SUBJECT;
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
}
