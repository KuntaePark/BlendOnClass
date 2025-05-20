package com.blendonclass.dto.board;

import com.blendonclass.entity.SubmitBoard;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitShowDto {
    private Long sbId;
    private Long abId;
    private String writerName;
    private String writerEmail;
    private String context;
    private LocalDateTime writeTime;
    private String fileUrl;
    private Boolean isWriter;

    public static SubmitShowDto from(SubmitBoard submitBoard) {
        SubmitShowDto submitShowDto = new SubmitShowDto();
        submitShowDto.setSbId(submitBoard.getId());
        submitShowDto.setAbId(submitBoard.getAssignmentBoard().getId());
        submitShowDto.setWriterName(submitBoard.getAccount().getName());
        submitShowDto.setWriterEmail(submitBoard.getAccount().getEmail());
        submitShowDto.setContext(submitBoard.getContext());
        submitShowDto.setWriteTime(submitBoard.getWriteTime());
        submitShowDto.setFileUrl(submitBoard.getFileUrl());

        return submitShowDto;
    }
}
