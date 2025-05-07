package com.blendonclass.dto.admin;

/*
    계정 생성 시 아이디, 비밀번호 반환용
 */

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AccountGeneratedDto {
    private Long accountId;
    private String loginId;
    private String password;
}
