package com.blendonclass.dto;

import com.blendonclass.entity.AssignmentBoard;
import com.blendonclass.entity.SubmitBoard;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class AssignmentShowDto {
    private Long abId;
    private Long classroomId;
    private String writerName;
    private String writerEmail;
    private String title;
    private String context;
    private LocalDateTime writeTime;
    private LocalDate dueDate;
    private Boolean isWriter;
    private String fileUrl;

    public static AssignmentShowDto from(AssignmentBoard assignmentBoard) {
        AssignmentShowDto assignmentShowDto = new AssignmentShowDto();
        assignmentShowDto.setAbId(assignmentBoard.getId());
        assignmentShowDto.setClassroomId(assignmentBoard.getAuthority().getClassroom().getId());
        assignmentShowDto.setWriterName(assignmentBoard.getAuthority().getAccount().getName());
        assignmentShowDto.setWriterEmail(assignmentBoard.getAuthority().getAccount().getEmail());
        assignmentShowDto.setTitle(assignmentBoard.getTitle());
        assignmentShowDto.setContext(assignmentBoard.getContext());
        assignmentShowDto.setWriteTime(assignmentBoard.getWriteTime());
        assignmentShowDto.setFileUrl(assignmentBoard.getFileUrl());
        assignmentShowDto.setDueDate(assignmentBoard.getDueDate());
        return assignmentShowDto;
    }

    public static AssignmentShowDto assignmentShowDtofrom(SubmitBoard submitBoard) {
        AssignmentShowDto assignmentShowDto = new AssignmentShowDto();
        assignmentShowDto.setAbId(submitBoard.getId());
        assignmentShowDto.setContext(submitBoard.getContext());
        return assignmentShowDto;
    }
}
