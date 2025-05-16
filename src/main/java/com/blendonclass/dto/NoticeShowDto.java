package com.blendonclass.dto;

import com.blendonclass.entity.NoticeBoard;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class NoticeShowDto {
    private Long nbId;
    private String writerName;
    private String writerEmail;
    private String title;
    private String context;
    private LocalDateTime writeTime;
    private String fileUrl;
    private Boolean isWriter;

    public static NoticeShowDto from(NoticeBoard noticeBoard) {
        NoticeShowDto noticeShowDto = new NoticeShowDto();
        noticeShowDto.nbId = noticeBoard.getId();
        noticeShowDto.writerName = noticeBoard.getAuthority().getAccount().getName();
        noticeShowDto.writerEmail = noticeBoard.getAuthority().getAccount().getEmail();
        noticeShowDto.title = noticeBoard.getTitle();
        noticeShowDto.context = noticeBoard.getContext();
        noticeShowDto.writeTime = noticeBoard.getWriteTime();
        noticeShowDto.fileUrl = noticeBoard.getFileUrl();

        return noticeShowDto;
    }
}
