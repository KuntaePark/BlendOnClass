package com.blendonclass.dto.admin;

/*
    계정 목록 표시용
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter@Setter
public class AccountListDto {
    private Long id;
    private String loginId;
    private String name;
    private String email;
    private ROLE role;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AccountListDto from(Account account) {return modelMapper.map(account, AccountListDto.class);}

}
