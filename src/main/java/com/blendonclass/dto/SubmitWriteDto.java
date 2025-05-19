package com.blendonclass.dto;

import com.blendonclass.entity.SubmitBoard;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class SubmitWriteDto {
    private Long sbId;
    private Long abId;
    private Long writerId;
    private String context;

    public static SubmitWriteDto fromSubmitBoard(SubmitBoard board) {
        SubmitWriteDto dto = new SubmitWriteDto();
        dto.setSbId(board.getId());
        dto.setAbId(board.getAssignmentBoard().getId());
        dto.setWriterId(board.getAccount().getId());
        dto.setContext(board.getContext());
        return dto;
    }
}
