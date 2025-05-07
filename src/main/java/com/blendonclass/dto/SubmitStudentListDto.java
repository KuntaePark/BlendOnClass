package com.blendonclass.dto;

import lombok.*;

@Getter
@Setter
public class SubmitStudentListDto {
    private Long accountId;
    private Long abId;
    private String name;
    private String email;
    private boolean isSubmit;
}
