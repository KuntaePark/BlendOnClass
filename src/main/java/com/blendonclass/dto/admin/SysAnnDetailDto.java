package com.blendonclass.dto.admin;

/*
    공지 작성/수정/상세
 */

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class SysAnnDetailDto {
    private Long saId;
    private String title;
    private String context;
    private LocalDateTime writeTime;
}
