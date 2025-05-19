package com.blendonclass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProgRateCalcDto {
    private Long lessonId;
    private Float completeRate;
}
