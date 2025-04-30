package com.blendonclass.dto.admin;

/*
    시스템 공지 목록 표시용
 */

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class SysAnnListDto {
    private Long saId;
    private String title;
    private LocalDateTime writeTime;
}
