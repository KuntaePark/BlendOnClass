package com.blendonclass.dto.admin;

/*
   계정 생성/수정용
 */

import com.blendonclass.constant.ROLE;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AccountDto {
    private Long accountId = null;
    private String name;
    private String email;
    private ROLE role;
}
