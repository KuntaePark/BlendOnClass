package com.blendonclass.dto.admin;

/*
    시스템 공지 목록 표시용
 */

import com.blendonclass.entity.SystemAnnounce;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter@Setter
public class SysAnnListDto {
    private Long id;
    private String title;
    private LocalDateTime writeTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public static SysAnnListDto from(SystemAnnounce systemAnnounce) {
        return modelMapper.map(systemAnnounce, SysAnnListDto.class);
    }
}
