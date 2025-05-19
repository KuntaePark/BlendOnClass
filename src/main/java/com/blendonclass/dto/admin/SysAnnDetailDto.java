package com.blendonclass.dto.admin;

/*
    공지 작성/수정/상세
 */

import com.blendonclass.entity.SystemAnnounce;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class SysAnnDetailDto {
    private Long saId;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String context;
    private LocalDateTime writeTime;

    public SystemAnnounce to() {
        SystemAnnounce announce = new SystemAnnounce();
        announce.setId(saId);
        announce.setTitle(title);
        announce.setContext(context);
        return announce;
    }

    public static SysAnnDetailDto from(SystemAnnounce announce) {
        SysAnnDetailDto dto = new SysAnnDetailDto();
        dto.setSaId(announce.getId());
        dto.setTitle(announce.getTitle());
        dto.setContext(announce.getContext());
        dto.setWriteTime(announce.getWriteTime());
        return dto;
    }
}
