package com.blendonclass.dto.board;

import com.blendonclass.entity.SubmitBoard;
import lombok.*;

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
