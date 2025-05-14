package com.blendonclass.dto;

import com.blendonclass.entity.Account;
import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.SubmitBoard;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class SubmitStudentListDto {
    private Long accountId;
    private Long abId;
    private String name;
    private String email;
    private boolean isSubmit;


}
