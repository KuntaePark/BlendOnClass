package com.blendonclass.dto.admin;

/*
    권한 목록 표시용
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.constant.SUBJECT;
import com.blendonclass.entity.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AuthListDto {
    private Long id;
    private String loginId;
    private String name;
    private String email;
    private ROLE role;
    private String aType;

    public static AuthListDto from(Authority authority) {
        AuthListDto dto = new AuthListDto();
        dto.setId(authority.getId());
        dto.setLoginId(authority.getAccount().getLoginId());
        dto.setName(authority.getAccount().getName());
        dto.setEmail(authority.getAccount().getEmail());
        dto.setRole(authority.getAccount().getRole());
        SUBJECT aType = authority.getAuthType();
        dto.setAType(aType == null ? "" : aType.getSubject());
        return dto;
    }

}
