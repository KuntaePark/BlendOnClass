package com.blendonclass.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class SubmitStudentListDto {
    private Long accountId;
    private Long abId;
    private Long sbId;
    private String name;
    private String email;
    private boolean isSubmit;


}
