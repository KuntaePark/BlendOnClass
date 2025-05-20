package com.blendonclass.dto.board;

import com.blendonclass.entity.AssignmentBoard;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class AssignmentWriteDto {
    private Long id;
    private Long writerId;
    private String title;
    private Long classroomId;
    private String context;
    private LocalDate dueDate;

    public static AssignmentWriteDto from(AssignmentBoard assignmentBoard) {
        AssignmentWriteDto dto = new AssignmentWriteDto();
        dto.setId(assignmentBoard.getId());
        dto.setWriterId(assignmentBoard.getAuthority().getAccount().getId());
        dto.setTitle(assignmentBoard.getTitle());
        dto.setContext(assignmentBoard.getContext());
        dto.setDueDate(assignmentBoard.getDueDate());
        return dto;
    }
}
