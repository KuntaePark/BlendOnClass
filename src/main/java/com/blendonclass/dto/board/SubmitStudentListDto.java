package com.blendonclass.dto.board;

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
    private Boolean isSubmit;
}
